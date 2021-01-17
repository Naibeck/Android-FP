package com.naibeck.newscorp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.naibeck.newscorp.R
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.databinding.ItemImageBinding
import com.naibeck.newscorp.ui.extension.loadUrl

class ImagesAdapter(
    val placeholderImages: List<PlaceholderImageItem>,
    private val imagesView: ImagesView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ImagesViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_image, parent, false), imagesView)

    override fun getItemCount(): Int = placeholderImages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImagesViewHolder -> holder.bind(placeholderImages[position])
            else -> throw IllegalArgumentException("Can't handle this type of holder")
        }
    }

    class ImagesViewHolder(view: View, private val imagesView: ImagesView) : RecyclerView.ViewHolder(view) {

        fun bind(item: PlaceholderImageItem) {
            val binding = DataBindingUtil.bind<ItemImageBinding>(itemView)
            binding?.imageThumbnail?.let {imageView ->
                imageView.loadUrl(item.thumbnailUrl)
                ViewCompat.setTransitionName(imageView, item.thumbnailUrl)
                imageView.setOnClickListener {
                    imagesView.onImageClick(it as ImageView, item.thumbnailUrl)
                }
            }
        }
    }
}
