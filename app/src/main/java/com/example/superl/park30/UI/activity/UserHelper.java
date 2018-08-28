package com.example.superl.park30.UI.activity;

import android.view.View;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/17.
 */

public class UserHelper extends BaseActivity {
    @Override
    public View setPage() {
        View view = View.inflate(UIUtils.getContext(), R.layout.userhepler_page, null);
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return check(true);
    }
}
