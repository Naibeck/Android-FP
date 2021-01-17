package com.naibeck.newscorp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.naibeck.newscorp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class PlaceholderImageDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placeholder_image_detail)

        supportPostponeEnterTransition()

        val imageView = findViewById<ImageView>(R.id.fullImage)
        imageView.transitionName = intent.getStringExtra("name")

        Picasso.get()
            .load(intent.getStringExtra("url"))
            .fit()
            .centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }
            })
    }
}