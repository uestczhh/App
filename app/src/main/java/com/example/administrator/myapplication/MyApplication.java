package com.example.administrator.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.administrator.myapplication.core.http.ProtocolUtils;
import com.example.administrator.myapplication.core.imageloader.ImageManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okhttputils.OkHttpUtils;

/**
 * Created by zhanghao on 2016/8/4.
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ImageManager.init(this);
        ProtocolUtils.init(this);
        OkHttpUtils.init(this);
    }
}
