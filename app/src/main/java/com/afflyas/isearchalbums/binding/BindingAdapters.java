package com.afflyas.isearchalbums.binding;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

/**
 *
 * Additional attributes for xml views using data binding
 *
 */
public class BindingAdapters {

    /**
     * Set visibility by passing boolean value
     */
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Load image from url using Glide
     */
    @BindingAdapter("imageFromUrl")
    public static void loadImageFromUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
