package com.naibeck.newscorp.ui.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter
fun loadUrl(imageView: ImageView, url: String) {
    Picasso.get()
        .load(url)
        .into(imageView)
}