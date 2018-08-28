package com.example.superl.park30.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;


/**
 * Created by SuperL on 2018/7/29.
 */

public class ParkApplication extends Application {
    private static final String TAG = "ParkApplication";
    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();  //获得当前线程ID，此处是主线程
        Log.i(TAG, "onCreate: ");
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
