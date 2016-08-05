package com.example.administrator.myapplication.newspackage.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.core.imageloader.ImageManager;
import com.example.administrator.myapplication.newspackage.bean.GuoNeiNewsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 国内新闻适配器
 * Created by zhanghao on 2016/8/5.
 */
public class GuoNeiNewsAdapter extends BaseRecyclerAdapter<GuoNeiNewsAdapter.MyHolder> {
    private Context mContext;
    private List<GuoNeiNewsBean> listData;
    private LayoutInflater inflater;
    private int lastPosition = -1;

    public GuoNeiNewsAdapter(Context mContext, List<GuoNeiNewsBean> listData) {
        this.mContext = mContext;
        this.listData = listData;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<GuoNeiNewsBean> listBean) {
        listData.clear();
        listData.addAll(listBean);
        notifyDataSetChanged();
    }

    public void addData(List<GuoNeiNewsBean> listBean) {
        listData.addAll(listBean);
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_guonei_news, null);
        return new MyHolder(view);
    }

    @Override
    public MyHolder getViewHolder(View view) {
        return null;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position, boolean isItem) {
        GuoNeiNewsBean bean = listData.get(position);
        ImageManager.loadImage(holder.simpleView, bean.picUrl);
        if (!TextUtils.isEmpty(bean.title)) {
            holder.title.setText(bean.title);
        }
        if (!TextUtils.isEmpty(bean.ctime)) {
            holder.date.setText(bean.ctime);
        }
        if (!TextUtils.isEmpty(bean.description)) {
            holder.desc.setText(bean.description);
        }
        setAnimation(holder.card, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                    .anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getAdapterItemCount() {
        return listData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        CardView card;
        SimpleDraweeView simpleView;
        TextView title;
        TextView desc;
        TextView date;

        public MyHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            simpleView = (SimpleDraweeView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            desc = (TextView) itemView.findViewById(R.id.tv_description);
            date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
