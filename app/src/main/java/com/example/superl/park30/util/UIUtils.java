package com.example.superl.park30.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.example.superl.park30.global.ParkApplication;


/**
 * Created by SuperL on 2018/7/29.
 */

public class UIUtils {

    public static Context getContext(){
        return ParkApplication.getContext();
    }
    public static Handler getHandler(){
        return ParkApplication.getHandler();
    }
    public static int getMainThreadId(){
        return ParkApplication.getMainThreadId();
    }

    //----------------加载资源文件-------------------------
    //获取字符串
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }
    //获取字符串数组
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }
    //获取图片
    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }
    //获取颜色
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }
    //获取尺寸
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);
    }
    //----------------尺寸转换 dp 与 px-------------------------
    //， dp 转 px
    public static int dipTopx(int dip){
        //获取设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }
    public static float pxTodip(int px){
        //获取设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }
    //----------------加载布局文件-------------------------
    public static View inflate(int id){
        return View.inflate(getContext(),id, null);
    }
    //判断是否运行在主线程
    public static boolean isRunOnUIThread(){
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()){
            return true;
        }
        return false;
    }
    //运行到主线程
    public static void runOnUIThread(Runnable r){
        if (isRunOnUIThread()){
            //已经是主线程，直接运行
            r.run();
        }else {
            //如果是子线程，借助Handler让其运行在主线程
            getHandler().post(r);
        }
    }

    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getContext().getResources().getColorStateList(mTabTextColorResId);
    }

    public static int argb(float alpha, float red, float green, float blue) {
        return ((int) (alpha * 255.0f + 0.5f) << 24) |
                ((int) (red   * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) <<  8) |
                (int) (blue  * 255.0f + 0.5f);
    }
}
