package com.naibeck.newscorp

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.io.applicativeError.attempt
import arrow.fx.extensions.io.concurrent.concurrent
import arrow.fx.extensions.io.unsafeRun.runBlocking
import arrow.unsafe
import com.naibeck.newscorp.data.network.PlaceholderImageApiService
import com.naibeck.newscorp.data.network.dto.PlaceholderImageItem
import com.naibeck.newscorp.data.network.error.NetworkError
import com.naibeck.newscorp.data.network.loadImages
import com.naibeck.newscorp.runtime.context.Runtime
import com.naibeck.newscorp.runtime.context.RuntimeContext
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
        val runtimeContext = RuntimeContext(
            testDispatcher,
            testDispatcher,
            service
        )

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

    @Test
    fun `map successful images placeholder image items`() {
        rule.server.whenever(Method.GET, "photos")
            .thenRespond(success(jsonBody = fileBody("ImagesPlaceholder.json")))

        unsafe {
            val images = runBlocking {
                runtime.loadImages()
            }

            images eq expectedImages()
        }
    }

    @Test
    fun `map 404 error for network response`() {
        rule.server.whenever(Method.GET, "photos")
            .thenRespond(me.jorgecastillo.hiroaki.models.error(code = 404))

        unsafe {
            val res: Either<Throwable, List<PlaceholderImageItem>> = runBlocking {
                val op = runtime.loadImages().attempt()
                op.map {
                    it.fold(
                        ifLeft = { error -> error.left() },
                        ifRight = { news -> news.right() }
                    )
                }
            }

            res eq NetworkError.NotFound.left()
        }
    }

    @Test
    fun `map unspecified error for network response`() {
        rule.server.whenever(Method.GET, "photos")
            .thenRespond(me.jorgecastillo.hiroaki.models.error(code = 500))

        unsafe {
            val res: Either<Throwable, List<PlaceholderImageItem>> = runBlocking {
                val op = runtime.loadImages().attempt()
                op.map {
                    it.fold(
                        ifLeft = { error -> error.left() },
                        ifRight = { news -> news.right() }
                    )
                }
            }

            res eq NetworkError.ServerError.left()
        }
    }

    private fun expectedImages(): List<PlaceholderImageItem> = listOf(
        PlaceholderImageItem(thumbnailUrl = "https://via.placeholder.com/150/92c952"),
        PlaceholderImageItem(thumbnailUrl = "https://via.placeholder.com/150/771796"),
        PlaceholderImageItem(thumbnailUrl = "https://via.placeholder.com/150/24f355")
    )
}
