package com.example.administrator.myapplication.core.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.administrator.myapplication.R;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * 图片管理类
 * Created by zhanghao on 2016/8/4.
 */
public class ImageManager {

    public final static String FILE_TYPE = "file:// ";

    public static void init(Context context) {
        Fresco.initialize(context);
    }


    private static void updateDefaultLoadStyle(GenericDraweeView draweeView) {
        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();

        //修改占位图为资源id:
        hierarchy.setPlaceholderImage(R.color.gray_9898);
        //修改缩放类型:
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        hierarchy.setFadeDuration(300);
        hierarchy.setFadeDuration(300);


//        如果你选择缩放类型为 focusCrop，需要指定一个居中点:
////        hierarchy.setActualImageFocusPoint(point);
//        你可以为图像添加一个 color filter:
//
//        ColorFilter filter;
//// 创建filter
//        hierarchy.setActualImageColorFilter(filter);


    }

    public static void loadImage(GenericDraweeView draweeView, String path) {
        if (draweeView == null || TextUtils.isEmpty(path)) {
            return;
        }
        Uri uri = Uri.parse(path);
        updateDefaultLoadStyle(draweeView);
        draweeView.setImageURI(uri);

    }


    public static void loadImage(GenericDraweeView draweeView, final String path, final ImageLoadListener imageLoadListener) {
        if (draweeView == null) {
            return;
        }
        Uri uri = Uri.parse(path);
        updateDefaultLoadStyle(draweeView);
        draweeView.setImageURI(uri);

        Postprocessor redMeshPostprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "redMeshPostprocessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                if (imageLoadListener != null && bitmap != null) {
                    imageLoadListener.onLoadingComplete(path, null, bitmap);
                }
            }

        };

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                FLog.d("Final image received! " +
                                "Size %d x %d",
                        "Quality level %d, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                if (imageLoadListener != null) {
                    imageLoadListener.onLoadingFailed(path, null, null);
                }
            }
        };


        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(redMeshPostprocessor)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request).setControllerListener(controllerListener)
                        .setOldController(draweeView.getController())
                        // other setters as you need
                        .build();

        draweeView.setController(controller);


    }
}
