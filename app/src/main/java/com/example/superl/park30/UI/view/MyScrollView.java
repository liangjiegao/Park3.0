package com.example.superl.park30.UI.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by SuperL on 2018/8/18.
 */

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        listener.onScrollChange(t);
    }

    public OnScrollChangeListener listener;
    public void setOnScrollChangeListener( OnScrollChangeListener listener){
        this.listener = listener;
    }
    public interface OnScrollChangeListener{

        void onScrollChange(int top);

    }
}
