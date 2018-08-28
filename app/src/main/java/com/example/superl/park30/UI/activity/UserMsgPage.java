package com.example.superl.park30.UI.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.entry.Image;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.superl.park30.R;
import com.example.superl.park30.UI.adapter.MyBaseAdapter;
import com.example.superl.park30.UI.holder.BaseHolder;
import com.example.superl.park30.UI.holder.UserMsgHolder;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.domain.UserMsg;
import com.example.superl.park30.util.LogUtils;
import com.example.superl.park30.util.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by SuperL on 2018/8/17.
 */

public class UserMsgPage extends BaseActivity {

    private static final int REQUEST_CODE = 0x00000011;

    public static final String SET_HEAD = "set_head";
    private View view;
    private ListView userMsgListView;

    //private String[] keys = {"昵称", "姓名", "实名认证", "手机号", "性别", "微信", "QQ"};
    private ArrayList<String> keys = new ArrayList<>();

    private View headView;
    private ImageView ivHead;
    private FrameLayout flPop;
    private View popview;
    private FrameLayout cover;

    //变量
    private boolean popIsOpen = false;  //标记当前性别选择框是否打开
    private static float alpha = 0f;                   //覆盖的FrameLayout的透明度
    private int pageHeight;
    private int mPopHeight;
    private RelativeLayout mRootLayout;
    private boolean isAnim;
    private int popShowHeight;
    private int screenHeight;
    private String imgHeadPath;
    private boolean headIsChange = false;

    //加载成功回调该方法
    @Override
    public View setPage() {
        view = View.inflate(UIUtils.getContext(), R.layout.user_msg, null);
        initView();
        initData();
        initListener();;
        return view;
    }
    private void initView(){
        headView = View.inflate(UIUtils.getContext(), R.layout.user_msg_list_top, null);
        userMsgListView = view.findViewById(R.id.user_msg_list);
        ivHead = headView.findViewById(R.id.iv_head);
        flPop = view.findViewById(R.id.fl_pop);
        popview = View.inflate(UIUtils.getContext(), R.layout.sex_select, null);
        cover = view.findViewById(R.id.fl_cover);
        mRootLayout = view.findViewById(R.id.tl_rootlayout);
    }
    private void initData(){
        userMsgListView.addHeaderView(headView);
        userMsgListView.setAdapter(new MyUserMsgAdapter(keys));
        Glide.with(this).load(new File(UserMsg.getmImgPath())).into(ivHead);
        //post到线程队列中， 等界面测量完在获取高度， 避免获取结果为0
        flPop.post(new Runnable() {
            @Override
            public void run() {
                getPopHeight();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) flPop.getLayoutParams();
                params.setMargins(15,pageHeight,15,0);  //将pop设置在Page的底部
                flPop.setLayoutParams(params);
                flPop.addView(popview);
            }
        });

        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
    }
    private void initListener(){
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只允许单选
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setCrop(true)  // 设置是否使用图片剪切功能。
                        .setSingle(true)  //设置是否单选
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(UserMsgPage.this, REQUEST_CODE); // 打开相册
            }
        });
        userMsgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i(position+"");
                toActivity(position, view);
            }
        });
    }
    private void toActivity(int position, View view){
        Intent intent;
        TextView tvValue = view.findViewById(R.id.tv_item_value);
        String value = tvValue.getText().toString();
        switch (position){
            case 1:
                intent = new Intent(this, ChangeUserNameActivity.class);
                intent.putExtra("userName", value);
                intent.putExtra("title", "昵称");
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, PhoneChangeActivity.class);
                intent.putExtra("phoneNum", value);
                intent.putExtra("title", "手机号");
                startActivity(intent);
                break;
            case 5:
                if (!isAnim){
                    doPop(pageHeight, mPopHeight);  //执行性别选择框开关动画
                }
                break;
            case 6:
                break;
                default:break;
        }
    }
    private void getPopHeight(){
        popview.measure(0,0);
        mPopHeight = popview.getMeasuredHeight();
        pageHeight = view.getMeasuredHeight();
    }

    //打开 或 关闭 性别选择框
    private void doPop(int pageHeight, int popHeight){
        LogUtils.i("isOpen" + popIsOpen);
        isAnim = true;
        ValueAnimator animator;
        if (!popIsOpen){
            popShowHeight = mPopHeight;
            animator = ValueAnimator.ofInt(pageHeight, pageHeight - popHeight);
        }else {
            popShowHeight = 0;
            animator = ValueAnimator.ofInt(pageHeight - popHeight, pageHeight);
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int marginTop = (int) animation.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) flPop.getLayoutParams();
                params.setMargins(15,marginTop,15,0);
                flPop.setLayoutParams(params);
                float fraction = animation.getAnimatedFraction();
                    if (!popIsOpen){
                        alpha = fraction;
                    }else {
                        alpha = 1.0f - fraction;
                    }
                if (alpha >= 0.8f){
                        alpha = 0.8f;
                }
                if (fraction == 1){
                    popIsOpen = !popIsOpen;
                    isAnim = false;
                }
                cover.setBackgroundColor(UIUtils.argb(alpha, 0,0,0));

            }
        });
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAnim){
            return false;
        }
        if (popIsOpen){
            return onTouchEvent(ev);
        }else {
            return super.dispatchTouchEvent(ev);
        }
    }
    float downY = 0;
    float moveY = 0;
    float detalY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //LogUtils.i("down");

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                moveY = event.getRawY();
                detalY = moveY - downY;
                LogUtils.i("detalY = " + detalY);
                if (detalY > 0){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) flPop.getLayoutParams();
                    popShowHeight = (int) (mPopHeight - detalY / 2);
                    params.setMargins(15, (int) (pageHeight - popShowHeight),15,0);
                    flPop.setLayoutParams(params);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (popIsOpen && detalY >= 0 && !isAnim){
                    doPop(pageHeight, popShowHeight);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i("UserMsg requestCode = " +requestCode + " resultCode = " + resultCode);
        if (requestCode == REQUEST_CODE && data != null){
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            imgHeadPath = images.get(0);
            UserMsg.setmImgPath(imgHeadPath);
            Glide.with(this).load(new File(imgHeadPath)).into(ivHead);
            headIsChange = true;
        }
    }

    //在子线程中执行
    @Override
    public LoadingPage.ResultState onLoad() {
        UserMsg userMsg = new UserMsg("梁杰高","梁杰高", "123456123", "15521168615");
        keys.add(userMsg.getUserName());
        keys.add(userMsg.getmTrueName());
        keys.add(userMsg.getCheck());
        keys.add(userMsg.getId());
        keys.add(userMsg.getSex());
        keys.add(userMsg.getWeChet());
        keys.add(userMsg.getQQ());

        return check(view != null);
    }

    @Override
    public void finish() {

        if (headIsChange){
            Intent intent = new Intent();
            intent.putExtra(SET_HEAD , imgHeadPath);
            setResult(MainActivity.RESULT_CODE, intent);
        }

        super.finish();
    }

    class MyUserMsgAdapter extends MyBaseAdapter<String>{

        public MyUserMsgAdapter(ArrayList<String> list) {
            super(list);
        }

        @Override
        public BaseHolder<String> getHolder() {
            UserMsgHolder holder = new UserMsgHolder();
            return holder;
        }
    }
}
