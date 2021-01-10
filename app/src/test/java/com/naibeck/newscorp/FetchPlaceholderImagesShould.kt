package com.naibeck.newscorp

import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.io.concurrent.concurrent
import arrow.fx.extensions.io.unsafeRun.runBlocking
import arrow.unsafe
import com.naibeck.newscorp.network.PlaceholderImageApiService
import com.naibeck.newscorp.network.loadImages
import com.naibeck.newscorp.runtime.Runtime
import com.naibeck.newscorp.runtime.RuntimeContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import me.jorgecastillo.hiroaki.*
import me.jorgecastillo.hiroaki.internal.MockServerRule
import me.jorgecastillo.hiroaki.models.fileBody
import me.jorgecastillo.hiroaki.models.success
import okhttp3.OkHttpClient

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class FetchPlaceholderImagesShould {

    @get:Rule
    val rule: MockServerRule = MockServerRule()

    private lateinit var runtime: Runtime<ForIO>

    @Before
    fun setup() {
        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .build()

        val service = rule.server.retrofitService(
            PlaceholderImageApiService::class.java,
            GsonConverterFactory.create(),
            httpClient)

        val testDispatcher = TestCoroutineDispatcher()
        val runtimeContext = RuntimeContext(testDispatcher, testDispatcher, service)

        runtime = object : Runtime<ForIO>(IO.concurrent(), runtimeContext) {}
    }

    @Test
    fun `send fetch images into expected path`() {
        rule.server.whenever(Method.GET, "photos")
            .thenRespond(success(jsonBody = fileBody("ImagesPlaceholder.json")))

        unsafe {
            runBlocking {
                runtime.loadImages()
            }
        }

        rule.server.verify("photos").called(
            times = once(),
            method = Method.GET)
    }
}
