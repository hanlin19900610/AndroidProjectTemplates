package com.mufeng.baselibrary.net

import com.alibaba.fastjson.JSON
import com.mufeng.baselibrary.utils.GsonUtils
import com.mufeng.baselibrary.BaseLibs
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import java.io.IOException
import java.lang.reflect.Type

@Parser(name = "ResultResponse")
open class ResultResponseParser<T> : TypeParser<Result<T>> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): Result<T> {
        val json = response.body?.string()
        val jsonObject = JSON.parseObject(json)
        val code = jsonObject.getIntValue(BaseLibs.httpConfig?.code)
        val msg = jsonObject.getString(BaseLibs.httpConfig?.msg)

        if (code != BaseLibs.httpConfig?.successCode) {
            BaseLibs.httpConfig?.handlerNetworkError(code)
            throw ParseException(code.toString(), msg, response)
        }

        if (code == BaseLibs.httpConfig?.successCode && types[0] === Any::class.java) {
            return Result(msg = msg, code = code, data = Any() as T)
        }

        if (code == BaseLibs.httpConfig?.successCode && types[0] === String::class.java) {
            return Result(
                msg = msg,
                code = code,
                data = jsonObject.getString(BaseLibs.httpConfig?.data) as T
            )
        }

        if (code == BaseLibs.httpConfig?.successCode && types[0] === Int::class.java) {
            return Result(
                msg = msg,
                code = code,
                data = jsonObject.getInteger(BaseLibs.httpConfig?.data) as T
            )
        }

        if (code == BaseLibs.httpConfig?.successCode && types[0] === Double::class.java) {
            return Result(
                msg = msg,
                code = code,
                data = jsonObject.getDouble(BaseLibs.httpConfig?.data) as T
            )
        }

        if (code == BaseLibs.httpConfig?.successCode && types[0] === Boolean::class.java) {
            return Result(
                msg = msg,
                code = code,
                data = jsonObject.getBoolean(BaseLibs.httpConfig?.data) as T
            )
        }
        try {
            val data = GsonUtils.INSTANCE.fromJson<T>(
                jsonObject.getString(BaseLibs.httpConfig?.data),
                types[0]
            )  //最后返回data字段
            return Result(msg = msg, code = code, data = data)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
