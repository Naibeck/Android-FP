package com.naibeck.newscorp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.naibeck.newscorp.ui.TestNewsApp

class CustomTestRunner : AndroidJUnitRunner() {
    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application = super.newApplication(cl, TestNewsApp::class.java.name, context)
}