package com.naibeck.newscorp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.naibeck.newscorp.R
import com.naibeck.newscorp.databinding.ActivityPlaceholderImageDetailBinding
import com.naibeck.newscorp.presentation.extension.loadTransitiveUrl
import com.squareup.picasso.Callback

class PlaceholderImageDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPlaceholderImageDetailBinding>(this, R.layout.activity_placeholder_image_detail)

        supportPostponeEnterTransition()

        binding.fullImage.apply {
            this.transitionName = intent.getStringExtra(MainActivity.IMAGE_NAME)
            intent.getStringExtra(MainActivity.IMAGE_URL)?.let {
                this.loadTransitiveUrl(it, handleImageLoad())
            }
        }
    }

    private fun handleImageLoad() =
        object : Callback {
            override fun onSuccess() {
                supportStartPostponedEnterTransition()
            }

            override fun onError(e: Exception?) {
                supportStartPostponedEnterTransition()
            }
        }
}