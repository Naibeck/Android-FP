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

open class NewsApplication : Application() {
    open var imagesService: PlaceholderImageApiService =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlaceholderImageApiService::class.java)

    open var runtimeContext =
        RuntimeContext(
            bgDispatcher = Dispatchers.IO,
            mainDispatcher = Dispatchers.Main,
            imageService = imagesService
        )

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

fun Context.getApp(): NewsApplication = applicationContext as NewsApplication
