package com.example.superl.park30.UI.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/14.
 */

public abstract class BaseActivity extends Activity {
    private static final String TAG = "BaseActivity";
    private FrameLayout frameLayout;
    private LoadingPage loadingPage;
    private ImageView back;
    private TextView pageTitle;
    public RelativeLayout mTopLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
        loadingPage.loadData();
    }
    private void initView(){
        /*getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        getWindow().setReenterTransition(explode);*/
        setContentView(R.layout.base_layout);
        mTopLayout = findViewById(R.id.rl_base_top_layout);
        back = findViewById(R.id.iv_back);
        frameLayout = findViewById(R.id.fl_page);
        loadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {

                return setPage();
            }

            @Override
            public ResultState onLoad() {
                return BaseActivity.this.onLoad();
            }
        };
        frameLayout.addView(loadingPage);
        pageTitle = findViewById(R.id.tv_page_title);
    }
    private void initData(){
        String title =getIntent().getStringExtra("title");
        pageTitle.setText(title);
    }
    private void initListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public LoadingPage.ResultState check(boolean isOk){
        return LoadingPage.ResultState.SUCCESS;
    }
    public abstract View setPage();
    public abstract LoadingPage.ResultState onLoad();
}
