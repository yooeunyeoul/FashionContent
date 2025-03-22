package com.example.stylefeed

import android.app.Application
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StyleFeedApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }
}