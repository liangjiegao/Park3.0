package com.example.superl.park30.UI.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/20.
 */

public class ChangeUserNameActivity extends BaseActivity {

    @Override
    public View setPage() {
        View view = View.inflate(UIUtils.getContext(), R.layout.change_username_item, null);
        final EditText et = view.findViewById(R.id.et_input);
        String text = getIntent().getStringExtra("userName");
        et.setText(text);

        ImageButton ib = view.findViewById(R.id.ib_delete);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return check(true);
    }
}
