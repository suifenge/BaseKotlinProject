package com.suifeng.kotlin.base.mvvm.binding.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * 图片布局绑定适配
 */
public class ImageBinding {

    @BindingAdapter("source")
    public static void bindImage(ImageView image, String source) {
        loadImage(image, source, null, null, false);
    }

    @BindingAdapter({"source", "isCircle"})
    public static void bindCircleImage(ImageView image, String source, boolean isCircle) {
        loadImage(image, source, null, null, isCircle);
    }

    @BindingAdapter({"source", "placeholder"})
    public static void bindPlaceHolderImage(ImageView image, String source, Drawable placeholder) {
        loadImage(image, source, placeholder, null, false);
    }

    @BindingAdapter({"source", "placeholder"})
    public static void bindPlaceHolderImage(ImageView image, String source, int placeholder) {
        loadImage(image, source, placeholder, -1, false);
    }

    @BindingAdapter({"source", "placeholder", "isCircle"})
    public static void bindPlaceHolderImageCircle(ImageView image, String source, Drawable placeholder, boolean isCircle) {
        loadImage(image, source, placeholder, null, isCircle);
    }

    @BindingAdapter({"source", "placeholder", "isCircle"})
    public static void bindPlaceHolderImageCircle(ImageView image, String source, int placeholder, boolean isCircle) {
        loadImage(image, source, placeholder, -1, isCircle);
    }

    @BindingAdapter({"source", "errorholder"})
    public static void bindErrorImage(ImageView image, String source, Drawable errorholder) {
        loadImage(image, source, null, errorholder, false);
    }

    @BindingAdapter({"source", "errorholder"})
    public static void bindErrorImage(ImageView image, String source, int errorholder) {
        loadImage(image, source, -1, errorholder, false);
    }

    @BindingAdapter({"source", "errorholder", "isCircle"})
    public static void bindErrorImageCircle(ImageView image, String source, Drawable errorholder, boolean isCircle) {
        loadImage(image, source, null, errorholder, isCircle);
    }

    @BindingAdapter({"source", "errorholder", "isCircle"})
    public static void bindErrorImageCircle(ImageView image, String source, int errorholder, boolean isCircle) {
        loadImage(image, source, -1, errorholder, isCircle);
    }

    @BindingAdapter({"source", "placeholder", "errorholder"})
    public static void bindHolderImage(ImageView image, String source, Drawable placeholder, Drawable errorholder) {
        loadImage(image, source, placeholder, errorholder, false);
    }

    @BindingAdapter({"source", "placeholder", "errorholder"})
    public static void bindHolderImage(ImageView image, String source, int placeholder, int errorholder) {
        loadImage(image, source, placeholder, errorholder, false);
    }

    @BindingAdapter({"source", "placeholder", "errorholder", "isCircle"})
    public static void bindHolderImageCircle(ImageView image, String source, Drawable placeholder, Drawable errorholder, boolean isCircle) {
        loadImage(image, source, placeholder, errorholder, isCircle);
    }

    @BindingAdapter({"source", "placeholder", "errorholder", "isCircle"})
    public static void bindHolderImageCircle(ImageView image, String source, int placeholder, int errorholder, boolean isCircle) {
        loadImage(image, source, placeholder, errorholder, isCircle);
    }


    /**
     * 加载任意资源类型图片
     *
     * @param image       控件
     * @param source      资源路径，可以是网址、文件、res id
     * @param placeholder 占位图
     * @param errorholder 错误图
     * @param isCircle    是否裁剪成圆
     */

    private static void loadImage(ImageView image, String source, Drawable placeholder, Drawable errorholder, boolean isCircle) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder)
                .error(errorholder);
        if (isCircle) {
            requestOptions = requestOptions.circleCrop();
        }

        Glide.with(image.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(source)
                .into(image);
    }

    /**
     * 加载图片
     * @param image widget
     * @param source 资源路径
     * @param placeholder 站位图
     * @param errorholder 错误图
     * @param isCircle 是否裁剪成圆
     */
    private static void loadImage(ImageView image, String source, int placeholder, int errorholder, boolean isCircle) {
        RequestOptions requestOptions = new RequestOptions();
        if(placeholder != -1) {
            requestOptions = requestOptions.placeholder(placeholder);
        }
        if(errorholder != -1) {
            requestOptions = requestOptions.error(errorholder);
        }
        if (isCircle) {
            requestOptions = requestOptions.circleCrop();
        }

        Glide.with(image.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(source)
                .into(image);
    }

}
