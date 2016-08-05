package com.example.administrator.myapplication.core.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.administrator.myapplication.common.config.AppConfig;
import com.example.administrator.myapplication.common.utils.CommonUtil;
import com.example.administrator.myapplication.common.utils.JsonUtils;
import com.example.administrator.myapplication.common.utils.MD5Utils;
import com.example.administrator.myapplication.common.utils.NetWorkUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.model.HttpParams;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by zhanghao on 2016/8/4.
 */
public class BaseProtocol implements RequestMethod {
//    public static final String TAG = "BaseProtocol";

    private Context mContext;
    private Handler handler = new Handler(Looper.getMainLooper());
    public static String mTag;//


    public BaseProtocol(Context context, String tag) {
        mContext = context.getApplicationContext();
        mTag = tag;


    }


    protected void requestPost(boolean cacheData, String path, JSONObject args, ResultCallback callback) {
        requestPostInternal(cacheData, false, path, args, callback);
    }

    protected void requestPost(boolean cacheData, String path, JSONObject args, JSONObject fileArgs, ResultCallback protocolJsonCallback) {
        requestPostInternal(cacheData, false, path, args,
                fileArgs, protocolJsonCallback);
    }

//    protected void requestGet(boolean cacheData, String path, JSONObject args, ResultCallback protocolJsonCallback) {
//        requestGetInternal(cacheData, false, path, args, protocolJsonCallback);
//
//
//    }


    /**
     * 检测网络
     *
     * @return
     */
    private boolean checkNetwork(final ResultCallback callback, boolean asyncResponse) {
        if (!NetWorkUtils.isNetworkConected(mContext)) {
            if (callback != null) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        callback.onNetworkError();
                    }
                };
                if (asyncResponse) {
                    runnable.run();
                } else {
                    handler.post(runnable);
                }
            }
            return false;
        }
        return true;
    }

//    protected void requestGetInternal(boolean cacheData, boolean asyncResponse, String path, JSONObject argsParams, ResultCallback callback) {
//        if (!checkNetwork(callback, asyncResponse)) {
//            return;
//        }
//
//
//        OkHttpUtils.get(AppConfig.HOST + path)
//                .tag(mTag)
//                .params(buildRequestEntity(argsParams))
////                .headers(buildRequestHeader(argsParams))
//                .cacheKey(getCacheKey(path, argsParams))
//                .cacheMode(cacheData ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.DEFAULT)
//                .execute(callback);
//
//
//    }

    protected void requestPostInternal(boolean cacheData, boolean asyncResponse, String path, JSONObject argsParams, JSONObject fileArgs, ResultCallback callback) {
        if (!checkNetwork(callback, asyncResponse)) {
            return;
        }


        OkHttpUtils.post(AppConfig.HOST + path)
                .tag(mTag)
                .params(buildRequestEntity(argsParams))
                .params(buildUploadFileEntity(fileArgs))
//                .headers(buildRequestHeader(argsParams))
                .headers(new HttpHeaders())
                .cacheKey(mTag)
                .cacheMode(cacheData ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.DEFAULT)
                .execute(callback);
    }


    protected void requestPostInternal(boolean cacheData, boolean asyncResponse, String path, JSONObject argsParams, ResultCallback callback) {
        if (!checkNetwork(callback, asyncResponse)) {
            return;
        }
        OkHttpUtils.post(AppConfig.HOST + path)
                .tag(mTag)
                .params(buildRequestEntity(argsParams))
                .headers(new HttpHeaders())
                .cacheKey(mTag)
                .cacheMode(cacheData ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.DEFAULT)
                .execute(callback);
    }


    /**
     * 构建请求头部
     *
     * @param argObject
     * @return
     */
    private HttpHeaders buildRequestHeader(JSONObject argObject) {
        HttpHeaders httpHeaders = new HttpHeaders();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("version", "0");//求接口版本号
            jsonObject.put("platform", "1"); //客户端类别[1-Android,2-Ios]
            jsonObject.put("language", "0");//客户端语言[0-中文(简体),1-英语,2-中文(繁体)]
            jsonObject.put("device-id", CommonUtil.getIMEI(mContext));//设备IDFA/IMEI[40位以内,小写字母]
//            jsonObject.put("tokey", GlobalUser.getUserToken(mContext));//用户登录令牌
            jsonObject.put("timestamp", System.currentTimeMillis() / 1000);//客户端时间戳(依据配置要求时间是前后多少秒)
        } catch (Exception e) {
            e.printStackTrace();
        }

        //把接口的参数迭代出来，放到RequestParams
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            httpHeaders.put(key, URLEncoder.encode(jsonObject.optString(key)));
        }
        return httpHeaders;
    }


    protected HttpParams buildRequestEntity(JSONObject args) {
        return ProtocolUtils.buildRequestEntity(args, true);

    }

    protected HttpParams buildUploadFileEntity(JSONObject args) {
        return ProtocolUtils.buildUpLoadFileEntity(args);

    }

    protected String getCacheKey(String path, JSONObject args) {
        return ProtocolUtils.getCacheKey(path, args);

    }


    /**
     * 停止当前页面的网络请求
     * 暂时没有配合使用
     */
    private void stopReqeust() {
        //根据 Tag 取消请求
        OkHttpUtils.getInstance().cancelTag(mTag);
    }

    /**
     * 获取时间戳
     */
    private long getTimes() {
        return Calendar.getInstance(Locale.CHINA).getTimeInMillis();
    }


}
