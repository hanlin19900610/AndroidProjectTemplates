package com.mufeng.demo.config

import com.blankj.utilcode.util.DeviceUtils
import com.mufeng.baselibrary.config.HttpConfig

class HttpConfigImpl: HttpConfig {
    override val baseUrl: String
        get() = "http://tools.cretinzp.com/jokes"
    override val code: String
        get() = "code"
    override val msg: String
        get() = "msg"
    override val data: String
        get() = "data"
    override val successCode: Int
        get() = 200
    override val errorCode: Int
        get() = 0

    override fun httpRequestHeader(): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["projecttoken"] = "37DD34D8ADE148149E0D227928F6FE1B"
//        headers["token"] = "token"
        headers["uk"] = "${DeviceUtils.getUniqueDeviceId()}"
        headers["channel"] = "cretin_open_api"
        headers["app "] = "1.0.0;1,${DeviceUtils.getSDKVersionCode()}"
        headers["device"] = "${DeviceUtils.getManufacturer()};${DeviceUtils.getModel()}"

        return super.httpRequestHeader()
    }

    override fun handlerNetworkError(code: Int) {

    }
}