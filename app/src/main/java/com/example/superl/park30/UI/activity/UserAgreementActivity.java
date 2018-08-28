package com.example.superl.park30.UI.activity;

import android.view.View;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/21.
 */

public class UserAgreementActivity extends BaseActivity {

    @Override
    public View setPage() {
        View view = View.inflate(UIUtils.getContext(), R.layout.user_agreement, null);

        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return check(true);
    }
}
