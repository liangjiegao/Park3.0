package com.example.superl.park30.UI.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.support.v4.widget.NestedScrollView;
import android.widget.Toast;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.UI.view.MyScrollView;
import com.example.superl.park30.domain.AppContext;
import com.example.superl.park30.util.LogUtils;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/17.
 * 当前预约页面
 */

public class OrderingPage extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "OrderingPage";
    //滑动时 要执行 动画
    private View view;  //根view
    private LinearLayout llDis; //主布局， 用户监听滑动来改变padding来下拉停车场图片
    private ImageView imgPark;
    private RelativeLayout rlTank;  //停车场图片的容器
    private float downY;    //当前点击的y坐标
    private int paddingTop; //当前主布局的paddingTop
    private int maxPadding;     //主布局最大paddingTop
    //布局交互控件
    private Button toIndoor;    //停车场实况
    private Button navigation;  //导航到这里
    private Button appointment; //立即预约

    //加载成功回调该方法加载布局
    @Override
    public View setPage() {
        view = View.inflate(UIUtils.getContext(), R.layout.park_msg, null);

        toIndoor = view.findViewById(R.id.btn_toIndoor);
        navigation = view.findViewById(R.id.btn_navigation);
        appointment = view.findViewById(R.id.park_msg_appointment);

        toIndoor.setOnClickListener(this);
        navigation.setOnClickListener(this);
        appointment.setOnClickListener(this);

        llDis = view.findViewById(R.id.ll_dis);
        rlTank = view.findViewById(R.id.rl_park_img_tank);
        //手动测量， 提前获取宽高
        rlTank.measure(0,0);
        int imgHeight = rlTank.getMeasuredHeight();
        paddingTop = -imgHeight;
        maxPadding = imgHeight;

        llDis.setPadding(0, paddingTop,0,0);

        return view;
    }
    //在子线程中执行， 加载所需要的数据
    @Override
    public LoadingPage.ResultState onLoad() {

        return check(view != null);
    }

    //根据当前滑动改变padding
    public void changeTopPadding(int pdy){
        llDis.setPadding(0, pdy, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                float detalY = moveY - downY;

                paddingTop += detalY;
                //为padding设置有效区间
                if (paddingTop >= 0){
                    paddingTop = 0;
                }else if (paddingTop <= -maxPadding){
                    paddingTop = -maxPadding;
                }

                changeTopPadding( paddingTop);
                downY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                int toValue;
                if (paddingTop > -maxPadding / 2){
                    toValue = 0;
                    //paddingTop = 0;
                }else {
                    //paddingTop = -maxPadding;
                    toValue = -maxPadding;
                }
                //当松开时， 执行相应动画
                ValueAnimator valueAnimator = ValueAnimator.ofInt(paddingTop , toValue);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        changeTopPadding(value);
                    }
                });
                valueAnimator.setInterpolator(new OvershootInterpolator());
                valueAnimator.setDuration(200);
                valueAnimator.start();
                paddingTop = toValue;
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        LogUtils.i("click");
        switch (v.getId()){
            case R.id.park_msg_appointment:
                LogUtils.i("appo");
                applyPark();
                break;
            case R.id.btn_toIndoor:
                Intent intent = new Intent(OrderingPage.this,IndoorParkActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_navigation:

                break;
        }
    }

    /**
     * 处理预约业务
     */
    private void applyPark(){
        if (AppContext.getSelectPos() != -1 && appointment.getText().equals("立即预约")){
            Toast.makeText(this,"预约成功", Toast.LENGTH_SHORT).show();
            AppContext.getCarArray().set(AppContext.getSelectPos(),1);
            appointment.setText("已预约");
            appointment.setTextColor(Color.BLACK);
            appointment.setBackground(getDrawable(R.drawable.gray_background));
        }
    }

}
