package com.mufeng.demo

import android.app.Application
import com.mufeng.baselibrary.BaseLibs
import com.mufeng.baselibrary.net.RxHttpManager
import com.mufeng.demo.config.HttpConfigImpl

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        BaseLibs.init(
            this,
            isDebug = true,
            httpConfig = HttpConfigImpl(),
        )

        RxHttpManager.init(this)
    }

}