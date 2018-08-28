package com.example.superl.park30.UI.holder;

import android.view.View;

/**
 * Created by SuperL on 2018/8/18.
 */
public abstract class BaseHolder<T> {

    private final View rootView;
    private T data;
    public BaseHolder(){
        rootView = initView();
        //3、打标记
        rootView.setTag(this);
    }
    //设置当前Item数据
    public void setData(T data, int pos) {
        this.data = data;
        refreshView(data, pos);
    }

    public T getData() {
        return data;
    }

    //1、加载布局文件
    //2、初始化控件
    public abstract View initView();
    //4、刷新界面
    public abstract void refreshView(T data, int pos);

    public View getRootView() {
        return rootView;
    }
}
