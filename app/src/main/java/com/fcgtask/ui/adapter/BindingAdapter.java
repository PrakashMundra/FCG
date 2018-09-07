package com.fcgtask.ui.adapter;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapter {
    @android.databinding.BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        if (url != null && !url.trim().isEmpty()) {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }
}
