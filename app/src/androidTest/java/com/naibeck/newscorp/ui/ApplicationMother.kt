package com.naibeck.newscorp.ui

import androidx.test.platform.app.InstrumentationRegistry

fun getApp() =
    (InstrumentationRegistry.getInstrumentation()
        .targetContext
        .applicationContext as TestNewsApp)