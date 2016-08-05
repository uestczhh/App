package com.example.administrator.myapplication.newspackage.iview;

import com.example.administrator.myapplication.newspackage.bean.GuoNeiNewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao on 2016/8/5.
 */
public interface GuoNeiNewsView {
    public void getDataSuccess(ArrayList<GuoNeiNewsBean> listBean, String message, int page);

    public void getDataFailed(String message);
}
