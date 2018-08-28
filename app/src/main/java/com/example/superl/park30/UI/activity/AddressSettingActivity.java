package com.example.superl.park30.UI.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.domain.Address;
import com.example.superl.park30.domain.UserMsg;
import com.example.superl.park30.util.LogUtils;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/21.
 */

public class AddressSettingActivity extends BaseActivity implements AddressSelectActivity.AddressSelectManager.OnItemSelectListener , View.OnClickListener{

    public static final int ENTER1 = 1;
    public static final int ENTER2 = 2;

    private RelativeLayout rlAdd1;
    private View view;
    private ImageView ivStar1;
    private ImageView ivStar2;
    private TextView tvAddress1;
    private TextView tvAddress2;
    private RelativeLayout rlAdd2;

    @Override
    public View setPage() {
        view = View.inflate(UIUtils.getContext(), R.layout.address_setting, null);

        initView();
        initData();
        initListener();

        //注册观察者模式， 用于监听选择的常用位置
        AddressSelectActivity.AddressSelectManager manager =
                AddressSelectActivity.AddressSelectManager.getAddressSelectManager();
        manager.registerObserver(this);

        return view;
    }
    private void initView(){
        rlAdd1 = view.findViewById(R.id.rl_address1_setting);
        rlAdd2 = view.findViewById(R.id.rl_address2_setting);
        ivStar1 = view.findViewById(R.id.iv_star1);
        ivStar2 = view.findViewById(R.id.iv_star2);
        tvAddress1 = view.findViewById(R.id.tv_address1);
        tvAddress2 = view.findViewById(R.id.tv_address2);

    }
    private void initData(){
        //获取当前用户是否有设置常用地址， 如果有设置，将常用地址显示出来
        if (UserMsg.getAddress1() != null){
            Address address1= UserMsg.getAddress1();
            ivStar1.setImageResource(R.mipmap.start1);
            tvAddress1.setText(address1.getName());
        }
        if (UserMsg.getAddress2() != null){
            Address address2= UserMsg.getAddress2();
            ivStar2.setImageResource(R.mipmap.start1);
            tvAddress2.setText(address2.getName());
        }
    }
    private void initListener(){
        rlAdd1.setOnClickListener(this);
        rlAdd2.setOnClickListener(this);
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return check(true);
    }

    @Override
    public void onItemSelect(Address address, int enter) {
        if (enter == ENTER1){
            ivStar1.setImageResource(R.mipmap.start1);
            tvAddress1.setText(address.getName());
            UserMsg.setAddress1(address);
        }else if (enter == ENTER2){
            ivStar2.setImageResource(R.mipmap.start1);
            tvAddress2.setText(address.getName());
            UserMsg.setAddress2(address);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.rl_address1_setting:
                intent = new Intent(AddressSettingActivity.this,AddressSelectActivity.class);
                intent.putExtra("enter", ENTER1);
                break;
            case R.id.rl_address2_setting:
                intent = new Intent(AddressSettingActivity.this,AddressSelectActivity.class);
                intent.putExtra("enter", ENTER2);
                break;
        }
        startActivity(intent);
    }
}
