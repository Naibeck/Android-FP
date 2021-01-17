package com.naibeck.newscorp

import me.jorgecastillo.hiroaki.Method
import me.jorgecastillo.hiroaki.internal.MockServerRule
import me.jorgecastillo.hiroaki.models.Body
import me.jorgecastillo.hiroaki.models.fileBody
import me.jorgecastillo.hiroaki.models.success
import me.jorgecastillo.hiroaki.whenever

fun MockServerRule.withJsonResponse(path: String, body: Body.JsonBodyFile) = this.server.whenever(Method.GET, path)
        .thenRespond(success(jsonBody = body))

fun MockServerRule.error404(path: String) = this.server.whenever(Method.GET, path)
    .thenRespond(me.jorgecastillo.hiroaki.models.error(code = 404))

fun MockServerRule.error500(path: String) = this.server.whenever(Method.GET, path)
    .thenRespond(me.jorgecastillo.hiroaki.models.error(code = 500))