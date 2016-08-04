package com.example.administrator.myapplication.core.imageloader;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 图片下载监听器
 * Created by waylenw on 16/6/3.
 */
public interface ImageLoadListener {
    /**
     * 开始下载
     *
     * @param imageUri Loading image URI
     * @param view     View for image
     */
    public void onLoadingStarted(String imageUri, View view);

    /**
     * 下载失败
     *
     * @param imageUri Loading image URI
     * @param view     View for image. Can be <b>null</b>.
     *                 loading was failed
     */
    public void onLoadingFailed(String imageUri, View view, String failReason);

    /**
     * 下载成功
     *
     * @param imageUri    Loaded image URI
     * @param view        View for image. Can be <b>null</b>.
     * @param loadedImage Bitmap of loaded and decoded image
     */
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

    /**
     * 下载取消
     *
     * @param imageUri Loading image URI
     * @param view     View for image. Can be <b>null</b>.
     */
    public void onLoadingCancelled(String imageUri, View view);
}
