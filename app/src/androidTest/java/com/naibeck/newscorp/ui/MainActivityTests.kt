package com.naibeck.newscorp.ui


import android.content.Intent
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.io.concurrent.concurrent
import com.naibeck.newscorp.R
import com.naibeck.newscorp.data.network.PlaceholderImageApiService
import com.naibeck.newscorp.error404
import com.naibeck.newscorp.runtime.context.Runtime
import com.naibeck.newscorp.runtime.context.RuntimeContext
import com.naibeck.newscorp.withDelayedResponse
import com.naibeck.newscorp.withJsonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.jorgecastillo.hiroaki.models.Body
import me.jorgecastillo.hiroaki.models.fileBody
import me.jorgecastillo.hiroaki.retrofitService
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.converter.gson.GsonConverterFactory

@LargeTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainActivityTests {

    @get:Rule val testRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var runtime: Runtime<ForIO>
    private lateinit var myserver: MockWebServer

    @Before
    fun setup() {
        myserver = MockWebServer()
        val myservice = myserver.retrofitService(
            PlaceholderImageApiService::class.java,
            GsonConverterFactory.create())

        val runtimeContext = RuntimeContext(
            Dispatchers.IO,
            Dispatchers.Main,
            myservice
        )

        getApp().imagesService = myservice
        getApp().runtimeContext = runtimeContext

        runtime = object : Runtime<ForIO>(IO.concurrent(), runtimeContext) {}
    }

    fun tearDown() {
        myserver.shutdown()
    }
    @Test
    fun show_progress_bar() {
        myserver.withDelayedResponse(
            path = "photos",
            body = Body.JsonBody(placeholderJson)
        )

        startActivity()

        onView(withId(R.id.progress)).check(matches(isDisplayed()))
    }

    @Test
    fun show_recycler_view() {
        myserver.withJsonResponse(
            path = "photos",
            body = fileBody("ImagesPlaceholder.json")
        )

        startActivity()

        onView(withId(R.id.images)).check(matches(isDisplayed()))
    }

    @Test
    fun show_loaded_item() {
        myserver.withJsonResponse(
            path = "photos",
            body = Body.JsonBody(placeholderJson)
        )

        startActivity()

        onView(withId(R.id.images)).perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        onView(withId(R.id.fullImage)).check(matches(isDisplayed()))
    }

    @Test
    fun show_error_view() {
        myserver.error404(path = "photos")

        startActivity()
        Thread.sleep(1000)
        onView(withId(R.id.errorView)).check(matches(isDisplayed()))
    }

    @Test
    fun refresh_after_pulling_down() {
        myserver.withDelayedResponse(
            path = "photos",
            body = Body.JsonBody(placeholderJson),
            delay = 3000
        )

        startActivity()

        onView(withId(R.id.refresh)).perform(swipeDown())
        onView(withId(R.id.refresh)).check(matches(isDisplayed()))
    }

    private fun startActivity(): MainActivity = runtime.run { testRule.launchActivity(Intent()) }

    private val placeholderJson: String = "[\n" +
            "  {\n" +
            "    \"albumId\": 1,\n" +
            "    \"id\": 1,\n" +
            "    \"title\": \"accusamus beatae ad facilis cum similique qui sunt\",\n" +
            "    \"url\": \"https://via.placeholder.com/600/92c952\",\n" +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/92c952\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"albumId\": 1,\n" +
            "    \"id\": 2,\n" +
            "    \"title\": \"reprehenderit est deserunt velit ipsam\",\n" +
            "    \"url\": \"https://via.placeholder.com/600/771796\",\n" +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/771796\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"albumId\": 1,\n" +
            "    \"id\": 3,\n" +
            "    \"title\": \"officia porro iure quia iusto qui ipsa ut modi\",\n" +
            "    \"url\": \"https://via.placeholder.com/600/24f355\",\n" +
            "    \"thumbnailUrl\": \"https://via.placeholder.com/150/24f355\"\n" +
            "  }\n" +
            "]\n"
}
