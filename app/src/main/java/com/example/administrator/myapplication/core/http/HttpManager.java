package com.example.administrator.myapplication.core.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.myapplication.common.utils.LogUtil;
import com.lzy.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求
 * Created by zhanghao on 2016/7/22.
 */
public class HttpManager {

    private OkHttpClient okHttpClient;
    private Handler handler;
    //    private String cookie = "";
    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    //请求成功码
    private static final int REQUEST_SUC = 200;
    private OkHttpClient.Builder okHttpClientBuilder;
    public static final int DEFAULT_MILLISECONDS = 60000; //默认的超时时间

    private HttpManager() {

        okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier());
        okHttpClientBuilder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
     * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
     * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
     */
    public class DefaultHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static HttpManager mInstance = null;

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 参数类
     */
    public static class Param {
        private String key;
        private String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 参数转化，map转params
     */
    private Param[] map2Params(Map<String, Object> map) {
        if (map == null) {
            return new Param[0];
        }
        int size = map.size();
        Param[] params = new Param[size];
        int i = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            params[i] = new Param();
            params[i].key = entry.getKey();
            params[i].value = entry.getValue() + "";
            i++;
        }
        return params;
    }

    /**
     * post请求构建reqeust
     */
    private Request buildPostRequest(String url, Map<String, Object> map, String tag) {
        Param[] params = map2Params(map);
//        FormBody body=FormBody.create()
//        FormEncodingBuilder builder = new FormEncodingBuilder();
        FormBody.Builder builder = new FormBody.Builder();

        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        FormBody formBody = builder.build();
//        RequestBody requestBody = builder.build();
        Request request = buildRequest(url, formBody, tag);
        return request;
    }

    /**
     * post请求构建json reqeust
     */
    private Request buildJsonPostRequest(String url, String json, String tag) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
        LogUtil.e("OkHttpClientManager", "请求接口：" + url);
        LogUtil.e("OkHttpClientManager", "请求body：" + json);

        Request request = buildRequest(url, requestBody, tag);
        return request;
    }

    /**
     * 构建请求对象
     *
     * @param url
     * @param requestBody
     * @param tag
     * @return
     */
    private Request buildRequest(String url, RequestBody requestBody, String tag) {
        Request.Builder builder = new Request.Builder();
////                .addHeader("x-token", SpSettingUtil.getInstance().getString(ConstantConfig.TOKEN))
//                .addHeader("x-client", ConstantConfig.PLATFORM_TYPE + "")
//                .addHeader("x-version", InfoUtil.getVersionName(LjBaseApplication.context()))
////                .addHeader("x-device",InfoUtil.getIMEI(LjBaseApplication.context()))
////                .addHeader("x-nettype",InfoUtil.getCurrentNetType(LjBaseApplication.context()))
////                .addHeader("x-area",InfoUtil.getLocation(LjBaseApplication.context()))
//                .url(url).post(requestBody);
////        builder.addHeader("x-token", SpSettingUtil.getInstance().getString(ConstantConfig.TOKEN));
//        //如果是退出就不带token过去，更换新token
//        if (url.equals(UrlConfig.BASE_USER_URL + UrlConfig.LOGINOUT_URL)) {
////            if (url.equals(UrlConfig.BASE_USER_URL + UrlConfig.LOGINOUT_URL) || url.equals(UrlConfig.BASE_USER_URL + UrlConfig.LOGIN_URL)){
//            builder.removeHeader("x-token");
//        } else {
//            if (!InfoUtil.isNull(SpSettingUtil.getInstance().getString(ConstantConfig.TOKEN))) {
////                System.out.println("x-token________"+SpSettingUtil.getInstance().getString(ConstantConfig.TOKEN));
//                builder.addHeader("x-token", SpSettingUtil.getInstance().getString(ConstantConfig.TOKEN));
//            }
//
//        }
//
//        if (tag != null) {
//            builder.tag(tag);
//        }
        return builder.build();
    }

    /**
     * get请求构建request
     */
    private Request buildGetRequest(String url, Map<String, Object> params, String tag) {
        if (params != null && !params.isEmpty()) {
            url += "?";
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                url += entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }
        Request.Builder builder = new Request.Builder()
//                .addHeader("x-token", SpSettingUtil.getInstance().getString(ConstantConfig.TOKEN))
//                .addHeader("x-client", ConstantConfig.PLATFORM_TYPE + "")
//                .addHeader("x-version", InfoUtil.getVersionName(LjBaseApplication.context()))
//                .addHeader("x-device",InfoUtil.getIMEI(LjBaseApplication.context()))
//                .addHeader("x-nettype",InfoUtil.getCurrentNetType(LjBaseApplication.context()))
//                .addHeader("x-area",InfoUtil.getLocation(LjBaseApplication.context()))
                .url(url);
//        if (!InfoUtil.isNull(SpSettingUtil.getInstance().getString(ConstantConfig.TOKEN))) {
//            builder.addHeader("x-token", SpSettingUtil.getInstance().getString(ConstantConfig.TOKEN));
//        }
        if (tag != null) {
            builder.tag(tag);
        }
        return builder.build();
    }


    /**
     * 发送请求成功消息
     *
     * @param
     * @param
     * @param cb
     */
    private void sendResponseSuccess(final Response response, final ResultCallback cb) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    cb.parseNetworkResponse(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 异步get请求
     */
    public void doGetRequest(final String cacheKey, String url, Map<String, Object> map, final ResultCallback cb) {
        doGetRequest(cacheKey, url, map, cb, "");
    }

    /**
     * 异步get请求
     */
    public void doGetRequest(final String cacheKey, final String url, Map<String, Object> map, final ResultCallback cb, String tag) {
        Request request = buildGetRequest(url, map, tag);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendResponseFail(e, cb);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendResponseSuccess(response, cb);
            }
        });
    }

    /**
     * 发送失败请求消息
     *
     * @param e
     * @param cb
     */
    private void sendResponseFail(final Exception e, final ResultCallback cb) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cb.onError(false, null, null, e);
            }
        });
    }

    public void doPostRequest(final String cacheKey, String url, Map<String, Object> map, final ResultCallback cb) {
        doPostRequest(cacheKey, url, map, cb, "");
    }

    /**
     * 异步post请求
     */
    public void doPostRequest(final String cacheKey, String url, Map<String, Object> map, final ResultCallback cb, String tag) {
        Request request = buildPostRequest(url, map, tag);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendResponseFail(e, cb);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendResponseSuccess(response, cb);
            }
        });
    }

    /**
     * 异步post请求
     */
    public void doPostJsonRequest(String url, String json, final ResultCallback cb) {
        doPostJsonRequest("", url, json, cb);
    }

    public void doPostJsonRequest(String url, String json, final ResultCallback cb, String tag) {
        doPostJsonRequest("", url, json, cb, tag);
    }

    /**
     * 异步post请求
     */
    public void doPostJsonRequest(final String cacheKey, String url, final String json, final ResultCallback cb) {
        doPostJsonRequest(cacheKey, url, json, cb, "");
    }

    /**
     * 异步post请求
     */
    public void doPostJsonRequestOne(String url, final String json, final ResultCallback cb) {
        doPostJsonRequest("", url, json, cb, "");
    }

    /**
     * 带标签的异步post请求
     *
     * @param cacheKey
     * @param url
     * @param json
     * @param cb
     * @param tag
     */
    public void doPostJsonRequest(final String cacheKey, String url, final String json,
                                  final ResultCallback cb, String tag) {
        Request request = buildJsonPostRequest(url, json, tag);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendResponseFail(e, cb);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendResponseSuccess(response, cb);
            }
        });
    }


    /**
     * 根据地址获取文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
