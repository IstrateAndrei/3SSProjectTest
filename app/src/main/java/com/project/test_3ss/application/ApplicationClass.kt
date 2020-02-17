package com.project.test_3ss.application

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.mcxiaoke.koi.KoiConfig
import com.orhanobut.hawk.Hawk
import com.project.test_3ss.koin.AppModules
import org.koin.android.ext.android.startKoin

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        KoiConfig.logEnabled = true //default is false
        KoiConfig.logLevel = Log.VERBOSE
        startKoin(this, AppModules.appModules)
        Hawk.init(this).build()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}