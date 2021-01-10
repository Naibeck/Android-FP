package com.naibeck.newscorp.data.network

import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import retrofit2.Call
import retrofit2.http.GET

interface PlaceholderImageApiService {
    @GET("photos")
    fun fetchImages(): Call<List<PlaceholderImageItem>>
}
