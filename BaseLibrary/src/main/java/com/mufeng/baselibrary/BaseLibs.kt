package com.mufeng.baselibrary

import android.app.Application
import android.graphics.Color
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import com.mufeng.baselibrary.utils.initContext
import com.mufeng.baselibrary.config.HttpConfig
import com.mufeng.baselibrary.config.HttpConfigImpl
import com.mufeng.baselibrary.utils.DataStoreUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV

object BaseLibs {
    var isDebug = true
    var defaultLogTag = "BaseLibrary"
    var sharedPrefName = "BaseLibrary"

    var httpConfig: HttpConfig? = null

    fun init(
        application: Application, isDebug: Boolean = true,
        defaultLogTag: String = BaseLibs.defaultLogTag,
        sharedPrefName: String = BaseLibs.sharedPrefName,
        httpConfig: HttpConfig = HttpConfigImpl()
    ) {
        this.isDebug = isDebug
        this.defaultLogTag = defaultLogTag
        this.sharedPrefName = sharedPrefName
        this.httpConfig = httpConfig
        initContext(application)
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.getDefaultMaker().setBgResource(R.drawable._ktx_toast_bg)
        ToastUtils.getDefaultMaker().setTextColor(Color.WHITE)
        initRefresh()
        MMKV.initialize(application)
        DataStoreUtils.init(application)
    }

    private fun initRefresh() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context)
        }
    }
}