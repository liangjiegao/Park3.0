package com.example.superl.park30.util;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by SuperL on 2018/8/1.
 */

public class BitmapHelper {

    private static BitmapUtils bitmapUtils = null;
    //单例， 烂汉模式
    public static BitmapUtils getBitmapUtils(){
        if (bitmapUtils == null){
            synchronized(BitmapHelper.class){
                if (bitmapUtils == null){
                    bitmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return bitmapUtils;
    }

}
