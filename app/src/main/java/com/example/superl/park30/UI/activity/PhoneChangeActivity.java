package com.example.superl.park30.UI.activity;

import android.view.View;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/21.
 */

public class PhoneChangeActivity extends BaseActivity {

    @Override
    public View setPage() {
        View view = View.inflate(UIUtils.getContext(), R.layout.change_phone_num, null);

        String text = getIntent().getStringExtra("phoneNum");
        TextView textView = view.findViewById(R.id.tv_phone);
        textView.setText("当前的手机号为"+text);
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return check(true);
    }
}
