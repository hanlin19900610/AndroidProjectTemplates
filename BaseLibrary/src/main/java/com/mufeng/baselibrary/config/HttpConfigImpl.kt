package com.mufeng.baselibrary.config

class HttpConfigImpl: HttpConfig {
    override val baseUrl: String
        get() = ""
    override val code: String
        get() = "code"
    override val msg: String
        get() = "msg"
    override val data: String
        get() = "data"
    override val successCode: Int
        get() = 1
    override val errorCode: Int
        get() = 0

    override fun handlerNetworkError(code: Int) {

    }
}