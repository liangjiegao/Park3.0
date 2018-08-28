package com.example.superl.park30.UI.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.superl.park30.R;
import com.example.superl.park30.util.LogUtils;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/17.
 */

public abstract class LoadingPage extends FrameLayout{

    public static int STATE_NOLOGIN = 0;
    public static int STATE_LOADING = 1;
    public static int STATE_SUCCESS = 2;
    public static int STATE_ERROR = 3;
    //当前状态为加载状态
    public int currentState = STATE_LOADING;

    private View onLoading;
    private View onSuccess;
    private View onError;

    public LoadingPage(@NonNull Context context) {
        super(context);
        init();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        if (onLoading == null){
            onLoading = View.inflate(UIUtils.getContext(), R.layout.loading_view, null);
        }
        addView(onLoading);

        if (onError == null){
            onError = View.inflate(UIUtils.getContext(), R.layout.error_view, null);
        }
        addView(onError);

        setRightShow();
    }

    public void setRightShow() {
        LogUtils.i("currentState : " + currentState);
        onLoading.setVisibility(currentState == STATE_LOADING ? VISIBLE : GONE);
        onError.setVisibility(currentState == STATE_ERROR ? VISIBLE : GONE);

        //如果加载成功页面为空并且当前状态为成功状态
        if (onSuccess == null && currentState == STATE_SUCCESS){
            onSuccess = onCreateSuccessView();
            if (onSuccess != null){
                addView(onSuccess);
            }
        }

        //判断是否显示加载成功页
        if (onSuccess != null){
            onSuccess.setVisibility(currentState == STATE_SUCCESS ? VISIBLE : GONE);
        }

    }
    public void loadData(){
        //网络加载用户信息 异步加载

        new Thread(){
            @Override
            public void run() {
                ResultState resultState = onLoad();
                currentState = resultState.getState();
                //主线程更新UI
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        setRightShow();

                    }
                });
            }
        }.start();


    }
    public abstract View onCreateSuccessView();
    public abstract ResultState onLoad();

    public enum ResultState{
        SUCCESS(STATE_SUCCESS),
        ERROR(STATE_ERROR);
        int state;
        private ResultState(int state){
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
