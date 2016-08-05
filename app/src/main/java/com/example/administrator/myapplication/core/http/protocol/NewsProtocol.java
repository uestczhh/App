package com.example.administrator.myapplication.core.http.protocol;

import android.content.Context;

import com.example.administrator.myapplication.common.config.AppConfig;
import com.example.administrator.myapplication.core.http.BaseProtocol;
import com.example.administrator.myapplication.core.http.HttpManager;
import com.example.administrator.myapplication.core.http.RequestMethod;
import com.example.administrator.myapplication.core.http.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by zhanghao on 2016/8/4.
 */
public class NewsProtocol extends BaseProtocol {


    public NewsProtocol(Context context, String tag) {
        super(context, tag);
    }

    /**
     * 获取国内新闻数据
     *
     * @param cacheData
     * @param page
     * @param rows
     * @param callback
     */
    public void getData(boolean cacheData, int page, int rows, ResultCallback callback) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("key", AppConfig.APP_KEY);
            jsonObject.put("page", page);
            jsonObject.put("rows", rows);
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestPost(cacheData, SUB_HOST_GUONEI, jsonObject, callback);
    }

}
