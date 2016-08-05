package com.example.administrator.myapplication.core.http;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.example.administrator.myapplication.BuildConfig;
import com.example.administrator.myapplication.common.utils.LogUtil;
import com.example.administrator.myapplication.common.utils.NetWorkUtils;
import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.request.BaseRequest;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhanghao on 2016/8/4.
 */
public abstract class ResultCallback<T> extends AbsCallback<T> {
    private static final String TAG = "ResultCallback";
    private Class clazz;
    private Type type;
    private Context mContext;
    private volatile String resposeBodyStr = "";//网络请求数据
    private boolean isArrayResult;//返回结果是否是数组

    public ResultCallback(Class clazz, Context context) {
        this.clazz = clazz;
        this.mContext = context.getApplicationContext();
    }

    public ResultCallback(Class clazz, Context context, boolean isArrayResult) {
        this.clazz = clazz;
        this.mContext = context.getApplicationContext();
        this.isArrayResult = isArrayResult;
    }


    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
//        //如果账户已经登录，就添加 token 等等

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("User-Agent", buildUserAgentStr());
        request.headers(httpHeaders);
    }

    private String buildUserAgentStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("My/");
        buffer.append(BuildConfig.VERSION_NAME);
        buffer.append(";");//客户端版本号
        buffer.append(ProtocolUtils.getNetType(mContext));//
        buffer.append(";");//网络类似
        buffer.append(Build.MODEL);
        buffer.append(";");//设备号
        buffer.append("Android ");
        buffer.append(Build.VERSION.SDK_INT);
        buffer.append(";");//Android版本
        buffer.append(Locale.getDefault().getCountry());//系统语言
        return buffer.toString();
    }


    //该方法是子线程处理，不能做ui相关的工作
    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData))
            return null;
        resposeBodyStr = responseData;


        String url = response.request().url().encodedPath();
        LogUtil.e(TAG, "reuest url:" + url);
        LogUtil.e(TAG, "parseNetworkResponse=" + responseData);
        Gson gson = new Gson();
        if (clazz != null) {
            return (T) gson.fromJson(responseData, clazz);
        }

        return null;
    }


    @Override
    public void onResponse(boolean isFromCache, T t, Request request, Response response) {
        onSucess(isFromCache, t, getResultMsg(response));
        resetData();
    }

    @Override
    public void onError(boolean isFromCache, Call call, Response response, Exception e) {
        super.onError(isFromCache, call, response, e);
        if (NetWorkUtils.isNetworkConected(mContext)) {
            onFail(isFromCache, getResultMsg(response), 0, response, e);
        } else {
            onNetworkError();
        }
        resetData();
    }

    protected abstract void onSucess(boolean isFromCache, T data, String msg);


    protected abstract void onFail(boolean isFromCache, String msg, int code, Response response, Exception e);

    protected abstract void onNetworkError();


    /**
     * 获取请求结果消息
     *
     * @return
     */
    public String getResultMsg(Response response) {
        String msg = "";

        try {
            if (response == null || TextUtils.isEmpty(resposeBodyStr)) {
                return "";
            }

            JSONObject jsonObject = new JSONObject(resposeBodyStr);
            msg = jsonObject.optString("M", "");
        } catch (Exception e) {
            return msg;
        }
        return msg;

    }

    private void resetData() {
        resposeBodyStr = "";
    }


}
