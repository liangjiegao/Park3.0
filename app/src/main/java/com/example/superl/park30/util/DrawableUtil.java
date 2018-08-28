package com.example.superl.park30.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by SuperL on 2018/8/3.
 */

public class DrawableUtil {

    public static GradientDrawable getGradientDrawable(int radius, int rgb){
        //在xml中定义的shape标签对应此类
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE); //设置形状为矩形

        shape.setCornerRadius(radius);  //设置圆角半径
        shape.setColor(rgb);

        return shape;
    }
    public static StateListDrawable getSelector(Drawable normal, Drawable press){

        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, press );
        bg.addState(new int[]{}, normal);

        return bg;
    }
    public static StateListDrawable getSelector(int clNormal, int clPress, int radius){
        GradientDrawable bgNormal = getGradientDrawable(radius, clNormal);
        GradientDrawable bgPress = getGradientDrawable(radius, clPress);

        StateListDrawable selector = getSelector(bgNormal, bgPress);

        return selector;

    }
}
