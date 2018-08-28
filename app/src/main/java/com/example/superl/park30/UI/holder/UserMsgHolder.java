package com.example.superl.park30.UI.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.domain.UserMsg;
import com.example.superl.park30.util.LogUtils;
import com.example.superl.park30.util.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SuperL on 2018/8/18.
 */

public class UserMsgHolder extends BaseHolder<String> {


    private View view;
    private TextView tvKey;
    private TextView tvValue;
    private ArrayList<String> keys;
    private ImageView iv;


    @Override
    public View initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.user_msg_item, null);
        keys = new ArrayList<>();
        tvKey = view.findViewById(R.id.tv_item_key);
        tvValue = view.findViewById(R.id.tv_item_value);
        iv = view.findViewById(R.id.iv_right);

        keys.add("昵称");
        keys.add("姓名");
        keys.add("实名认证");
        keys.add("手机号");
        keys.add("性别");
        keys.add("微信");
        keys.add("QQ");
        LogUtils.i("item height = " + view.getHeight() );
        return view;
    }

    @Override
    public void refreshView(String data, int pos) {
        tvKey.setText(keys.get(pos));
        tvValue.setText(data);
        if (keys.get(pos).equals("姓名") || keys.get(pos).equals("实名认证")){
            iv.setVisibility(View.INVISIBLE);

        }
        //view.setOnClickListener();
    }

    //interface
}
