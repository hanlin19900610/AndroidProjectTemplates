package com.mufeng.baselibrary.config

interface HttpConfig {

    // 接口请求地址
    val baseUrl: String

    // 请求的解析code
    val code: String
    val msg: String
    val data: String

    // 请求成功CODE
    val successCode: Int

    // 请求失败code
    val errorCode: Int

    // 请求失败code
    fun handlerNetworkError(code: Int)

    // 公共参数
    fun httpCommonParams(): Map<String, Any> = emptyMap()

    fun httpRequestHeader(): Map<String, String> = emptyMap()


}