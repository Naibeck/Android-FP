package com.naibeck.newscorp.presentation.views

import android.widget.ImageView
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem

interface ContainerView {
    fun showProgress()
    fun hideProgress()
    fun showError()
}

interface ImagesView {
    fun showProgress()
    fun hideProgress()
    fun show(images: List<PlaceholderImageItem>)
    fun onImageClick(imageView: ImageView?, url: String)
    fun showError()
}