package com.naibeck.newscorp.network

import com.naibeck.newscorp.network.dto.PlaceholderImageItem
import retrofit2.Call
import retrofit2.http.GET

interface PlaceholderImageApiService {
    @GET("photos")
    fun fetchImages(): Call<List<PlaceholderImageItem>>
}
