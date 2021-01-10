package com.naibeck.newscorp.runtime.context

import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.io.concurrent.concurrent
import arrow.fx.typeclasses.Concurrent
import com.naibeck.newscorp.network.PlaceholderImageApiService
import kotlinx.coroutines.CoroutineDispatcher

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class Runtime<F>(
    concurrent: Concurrent<F>,
    val context: RuntimeContext
) : Concurrent<F> by concurrent

fun IO.Companion.runtime(context: RuntimeContext) = object : Runtime<ForIO>(IO.concurrent(), context) {}

data class RuntimeContext(
    val bgDispatcher: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher,
    val imageService: PlaceholderImageApiService
)
