package com.example.administrator.myapplication.newspackage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.base.BaseFragment;
import com.example.administrator.myapplication.common.utils.LogUtil;
import com.example.administrator.myapplication.common.utils.ToastUtil;
import com.example.administrator.myapplication.core.http.protocol.NewsProtocol;
import com.example.administrator.myapplication.newspackage.adapter.GuoNeiNewsAdapter;
import com.example.administrator.myapplication.newspackage.bean.GuoNeiNewsBean;
import com.example.administrator.myapplication.newspackage.iview.GuoNeiNewsView;
import com.example.administrator.myapplication.newspackage.model.GuoNeiNewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao on 2016/8/4.
 */
public class NewsFragment extends BaseFragment implements GuoNeiNewsView {
    private static final String TAG = "NewsFragment";

    private static final int UPDATE_TYPE_REFRESH = 1;//下拉刷新
    private static final int UPDATE_TYPE_LOAD = 2;//上拉加载
    private static final int ROWS = 5;//每次加载数据个数
    private int mPage = 1;

    private RecyclerView recyclerView;
    private XRefreshView refreshView;

    private GuoNeiNewModel guoNeiNewModel;
    private GuoNeiNewsAdapter guoNeiNewsAdapter;

    private List<GuoNeiNewsBean> listData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, null);
        refreshView = (XRefreshView) root.findViewById(R.id.xrefresh);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        listData = new ArrayList<>();
        guoNeiNewModel = new GuoNeiNewModel(getContext(), this);
        guoNeiNewsAdapter = new GuoNeiNewsAdapter(getContext(), listData);
        recyclerView.setAdapter(guoNeiNewsAdapter);
        updateData(UPDATE_TYPE_REFRESH);
        setXRefresh();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setXRefresh() {
        refreshView.setPullLoadEnable(true);
//        refreshView.setSilenceLoadMore();
        refreshView.setPinnedTime(1000);
        refreshView.setMoveForHorizontal(true);
//        refreshView.setPreLoadCount(4);
        guoNeiNewsAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
        refreshView.enableReleaseToLoadMore(true);
        refreshView.enableRecyclerViewPullUp(true);
        refreshView.enablePullUpWhenLoadCompleted(true);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                updateData(UPDATE_TYPE_REFRESH);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                updateData(UPDATE_TYPE_LOAD);
            }
        });
    }

    private void updateData(int type) {
        if (type == UPDATE_TYPE_REFRESH) {
            mPage = 1;
            getData();
        } else {
            getData();
        }
    }

    private void getData() {
        guoNeiNewModel.getGuoNeiNews(false, mPage, ROWS);
    }

    @Override
    public void getDataSuccess(ArrayList<GuoNeiNewsBean> listBean, String message, int page) {
        if (listBean != null && listBean.size() > 0) {
            if (page == 1) {
                guoNeiNewsAdapter.setData(listBean);
                refreshView.stopRefresh();
            } else {
                guoNeiNewsAdapter.addData(listBean);
                refreshView.setLoadComplete(true);
            }
            mPage++;
        } else {
            if (page == 1) {
                //下拉没有数据
                refreshView.stopRefresh();
            } else {
                //上拉没有数据
                refreshView.stopLoadMore();
            }
        }

    }


    @Override
    public void getDataFailed(String message) {
        ToastUtil.show(message);
    }
}
