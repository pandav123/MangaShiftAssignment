package com.example.mangashelf

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MangaShelf : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}