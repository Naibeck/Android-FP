package com.naibeck.newscorp.runtime

import android.app.Application
import android.content.Context
import com.naibeck.newscorp.BuildConfig
import com.naibeck.newscorp.data.network.PlaceholderImageApiService
import com.naibeck.newscorp.runtime.context.RuntimeContext
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class NewsApplication : Application() {

    val runtimeContext by lazy {
        RuntimeContext(
            bgDispatcher = Dispatchers.IO,
            mainDispatcher = Dispatchers.Main,
            imageService = imagesService
        )
    }

    private val imagesService: PlaceholderImageApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(PlaceholderImageApiService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

fun Context.application(): NewsApplication = applicationContext as NewsApplication
