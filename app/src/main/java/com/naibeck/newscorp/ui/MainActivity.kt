package com.naibeck.newscorp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.naibeck.newscorp.R
import com.naibeck.newscorp.databinding.ActivityMainBinding
import com.naibeck.newscorp.presentation.ContainerView
import com.naibeck.newscorp.presentation.extension.hide
import com.naibeck.newscorp.presentation.extension.show

class MainActivity : AppCompatActivity(), ContainerView {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadFragment()
    }

    private fun loadFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlaceholderFragment.getInstance())
            .commit()
    }

    override fun showProgress() {
        binding?.progress?.show()
    }

    override fun hideProgress() {
        binding?.progress?.hide()
    }

//    override fun onImageClick(imageView: ImageView?, url: String) {
//        imageView?.let {
//            val intent = Intent(this, PlaceholderImageDetailActivity::class.java)
//            intent.putExtra(IMAGE_URL, url)
//            intent.putExtra(IMAGE_NAME, ViewCompat.getTransitionName(imageView))
//
//            startActivity(intent, generateTransitionOption(imageView).toBundle())
//        }
//    }

    override fun showError() {
        binding?.errorView?.container?.visibility = View.VISIBLE
    }
//
//    private fun generateTransitionOption(imageView: ImageView): ActivityOptionsCompat =
//        ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            imageView,
//            ViewCompat.getTransitionName(imageView) ?: "imageView"
//        )
//
    companion object {
        const val IMAGE_URL = "image.url"
        const val IMAGE_NAME = "image.name"
    }
}
