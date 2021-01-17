package com.naibeck.newscorp.ui.extension

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String) =
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .into(this)

fun ImageView.loadTransitiveUrl(url: String, callback: Callback) =
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .into(this, callback)

