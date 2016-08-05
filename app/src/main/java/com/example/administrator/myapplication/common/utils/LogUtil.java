package com.example.administrator.myapplication.common.utils;

import android.util.Log;

/**
 * Created by lihuarong on 2016/5/3.
 * LOG工具类
 */
public class LogUtil {
    //debug开关
    public static boolean flag = true;
    //统一的TAG
    public static String TAG = "lianj";

    public static void d(String tag,String msg){
        if (flag)
            Log.d(tag, msg);
    }
    public static void e(String tag,String msg){
        if (flag)
           Log.e(tag, msg);
    }
    public static void i(String tag,String msg){
        if (flag)
            Log.i(tag, msg);
    }
    public static void w(String tag,String msg){
        if (flag)
            Log.w(tag, msg);
    }
    public static void v(String tag,String msg){
        if (flag)
            Log.v(tag, msg);
    }

    public static void d(String msg){
        if (flag)
            Log.d(TAG, msg);
    }
    public static void e(String msg){
        if (flag)
            Log.e(TAG, msg);
    }
    public static void i(String msg){
        if (flag)
            Log.i(TAG, msg);
    }
    public static void w(String msg){
        if (flag)
            Log.w(TAG, msg);
    }
    public static void v(String msg){
        if (flag)
            Log.v(TAG, msg);
    }

}
