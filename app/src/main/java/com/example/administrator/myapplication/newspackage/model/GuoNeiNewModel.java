package com.example.administrator.myapplication.newspackage.model;

import android.content.Context;

import com.example.administrator.myapplication.common.utils.ToastUtil;
import com.example.administrator.myapplication.core.http.ResultCallback;
import com.example.administrator.myapplication.core.http.protocol.NewsProtocol;
import com.example.administrator.myapplication.newspackage.iview.GuoNeiNewsView;
import com.example.administrator.myapplication.newspackage.response.GuoNeiNewsResponse;

import okhttp3.Response;

/**
 * Created by zhanghao on 2016/8/5.
 */
public class GuoNeiNewModel {
    private static final String TAG = "GuoNeiNewModel";

    private Context context;
    private NewsProtocol newsProtocol;
    private GuoNeiNewsView guoNeiNewsView;

    public GuoNeiNewModel(Context context, GuoNeiNewsView guoNeiNewsView) {
        this.context = context;
        this.guoNeiNewsView = guoNeiNewsView;
        newsProtocol = new NewsProtocol(context, TAG);
    }

    public void getGuoNeiNews(boolean cacheData,final int page, int rows) {
        newsProtocol.getData(cacheData, page, rows, new ResultCallback<GuoNeiNewsResponse>(GuoNeiNewsResponse.class, context) {
            @Override
            protected void onSucess(boolean isFromCache, GuoNeiNewsResponse data, String msg) {
                if (data != null && data.error_code == 0) {
                    guoNeiNewsView.getDataSuccess(data.result, msg, page);
                } else {
                    guoNeiNewsView.getDataFailed(data.reason);
                }
            }

            @Override
            protected void onFail(boolean isFromCache, String msg, int code, Response response, Exception e) {
                guoNeiNewsView.getDataFailed(msg);
            }

            @Override
            protected void onNetworkError() {
                ToastUtil.show("网络异常");
            }
        });
    }
}
