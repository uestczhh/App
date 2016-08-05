package com.example.administrator.myapplication.common.utils;

import android.widget.Toast;

import com.example.administrator.myapplication.MyApplication;

/**
 * Created by lihuarong on 2016/5/4 10:32.
 * Toast工具类
 */
public class ToastUtil {

  private static Toast toast;

    /**
     * 显示Toast
     * @param msg 显示消息
     */
    public static void show(String msg){
        if (null == toast){
            toast = Toast.makeText(MyApplication.context,"",Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

    /**
     * 显示Toast
     * @param resId 显示消息的R地址
     */
    public static void show(int resId){
        if (null == toast){
            toast = Toast.makeText(MyApplication.context,"",Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }

}
