package com.naibeck.newscorp

import me.jorgecastillo.hiroaki.Method
import me.jorgecastillo.hiroaki.models.Body
import me.jorgecastillo.hiroaki.models.delay
import me.jorgecastillo.hiroaki.models.success
import me.jorgecastillo.hiroaki.whenever
import okhttp3.mockwebserver.MockWebServer

fun MockWebServer.withDelayedResponse(path: String, body: Body.JsonBody, delay: Long = 2000) = this.whenever(Method.GET, path)
    .thenRespond(success(jsonBody = body).delay(delay))

fun MockWebServer.withJsonResponse(path: String, body: Body.JsonBodyFile) = this.whenever(Method.GET, path)
    .thenRespond(success(jsonBody = body))

fun MockWebServer.withJsonResponse(path: String, body: Body.JsonBody) = this.whenever(Method.GET, path)
        .thenRespond(success(jsonBody = body))


fun MockWebServer.error404(path: String) = this.whenever(Method.GET, path)
    .thenRespond(me.jorgecastillo.hiroaki.models.error(code = 404))

fun MockWebServer.error500(path: String) = this.whenever(Method.GET, path)
    .thenRespond(me.jorgecastillo.hiroaki.models.error(code = 500))