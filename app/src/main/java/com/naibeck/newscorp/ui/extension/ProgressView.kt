package com.naibeck.newscorp.ui.extension

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    this.visibility = View.GONE
}