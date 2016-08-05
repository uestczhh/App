package com.example.administrator.myapplication.core.http;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.myapplication.BuildConfig;
import com.example.administrator.myapplication.common.utils.LogUtil;
import com.example.administrator.myapplication.common.utils.MD5Utils;
import com.example.administrator.myapplication.common.utils.NetWorkUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.model.HttpParams;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * Created by zhanghao on 2016/8/4.
 */
public class ProtocolUtils {

    /**
     * 网络请求框架初始化
     *
     * @param application
     */
    public static void init(Application application) {

        //必须调用初始化
        OkHttpUtils.init(application);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug(BaseProtocol.mTag)                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);               //全局的写入超时时间
    }

    /**
     * 构造post请求体
     *
     * @param jsonBody
     * @return
     */
    public static HttpParams buildRequestEntity(JSONObject jsonBody, boolean isFilterEmpty) {
        if (BuildConfig.DEBUG) {
            LogUtil.e(BaseProtocol.mTag, "***********************************************");
            LogUtil.e(BaseProtocol.mTag, "jsonBody=" + jsonBody);
            LogUtil.e(BaseProtocol.mTag, "***********************************************");
        }

        if (jsonBody == null) {
            jsonBody = new JSONObject();
        }

        HttpParams params = new HttpParams();
        //把接口的参数迭代出来，放到RequestParams
        Iterator<String> keys = jsonBody.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = String.valueOf(jsonBody.opt(key));
            if (isFilterEmpty && TextUtils.isEmpty(value)) {
                continue;
            }

            params.put(key, value);
        }

        return params;
    }

    /**
     * 构建上传文件实体
     *
     * @param jsonBody
     * @return
     */
    public static HttpParams buildUpLoadFileEntity(JSONObject jsonBody) {
        if (jsonBody == null) {
            jsonBody = new JSONObject();
        }

        HttpParams params = new HttpParams();
        //把接口的参数迭代出来，放到RequestParams
        Iterator<String> keys = jsonBody.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            params.put(key, (File) (jsonBody.opt(key)));
        }

        return params;
    }


    /**
     * 获取接口缓存保存的文件名
     */
    public static String getCacheKey(String method, JSONObject args) {
        String json = "";
        if (args != null) {
            json = args.toString();
        }
        String key = method + "@" + json;
        try {
            return MD5Utils.getMd5(key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前的网络类型
     */
    public static String getNetType(Context context) {
        int netType = NetWorkUtils.getNetworkType(context);
        switch (netType) {
            case NetWorkUtils.NETWORK_TYPE_2G:
                return "2G";
            case NetWorkUtils.NETWORK_TYPE_3G:
                return "3G";
            case NetWorkUtils.NETWORK_TYPE_4G:
                return "4G";
            case NetWorkUtils.NETWORK_TYPE_WIFI:
                return "WIFI";
            case NetWorkUtils.NETWORK_TYPE_NOT_AVAILABLE:
                return "NONE";
            default:
                return "UNKNWON";
        }
    }

}
