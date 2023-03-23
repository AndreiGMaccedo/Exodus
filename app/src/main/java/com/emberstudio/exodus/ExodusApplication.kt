package com.emberstudio.exodus

import android.app.Application
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class ExodusApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}