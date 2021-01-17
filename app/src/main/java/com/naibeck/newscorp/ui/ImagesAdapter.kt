package com.naibeck.newscorp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.naibeck.newscorp.R
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.databinding.ItemImageBinding
import com.squareup.picasso.Picasso
import java.lang.IllegalArgumentException

class ImagesAdapter(val placeholderImages: List<PlaceholderImageItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ImagesViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_image, parent, false))

    override fun getItemCount(): Int = placeholderImages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImagesViewHolder -> holder.bind(placeholderImages[position])
            else -> throw IllegalArgumentException("Can't handle this type of holder")
        }
    }
    class ImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: PlaceholderImageItem) {
            val binding = DataBindingUtil.bind<ItemImageBinding>(itemView)
            Picasso.get()
                .load(item.thumbnailUrl)
                .into(binding?.imageThumbnail)
        }
    }
}
