package com.mufeng.baselibrary.net

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import rxhttp.toFlowProgress
import rxhttp.wrapper.param.*

object Net {

    fun get(url: String, vararg formatArgs: Any): RxHttpNoBodyParam {
        return RxHttp.get(url, *formatArgs)
    }

    fun head(url: String, vararg formatArgs: Any): RxHttpNoBodyParam {
        return RxHttp.head(url, *formatArgs)
    }

    fun postBody(url: String, vararg formatArgs: Any): RxHttpBodyParam {
        return RxHttp.postBody(url, *formatArgs)
    }

    fun putBody(url: String, vararg formatArgs: Any): RxHttpBodyParam {
        return RxHttp.putBody(url, *formatArgs)
    }

    fun patchBody(url: String, vararg formatArgs: Any): RxHttpBodyParam {
        return RxHttp.patchBody(url, *formatArgs)
    }

    fun deleteBody(url: String, vararg formatArgs: Any): RxHttpBodyParam {
        return RxHttp.deleteBody(url, *formatArgs)
    }

    fun postForm(url: String, vararg formatArgs: Any): RxHttpFormParam {
        return RxHttp.postForm(url, *formatArgs)
    }

    fun putForm(url: String, vararg formatArgs: Any): RxHttpFormParam {
        return RxHttp.putForm(url, *formatArgs)
    }

    fun patchForm(url: String, vararg formatArgs: Any): RxHttpFormParam {
        return RxHttp.patchForm(url, *formatArgs)
    }

    fun deleteForm(url: String, vararg formatArgs: Any): RxHttpFormParam {
        return RxHttp.deleteForm(url, *formatArgs)
    }

    fun postJson(url: String, vararg formatArgs: Any): RxHttpJsonParam {
        return RxHttp.postJson(url, *formatArgs)
    }

    fun putJson(url: String, vararg formatArgs: Any): RxHttpJsonParam {
        return RxHttp.putJson(url, *formatArgs)
    }

    fun patchJson(url: String, vararg formatArgs: Any): RxHttpJsonParam {
        return RxHttp.patchJson(url, *formatArgs)
    }

    fun deleteJson(url: String, vararg formatArgs: Any): RxHttpJsonParam {
        return RxHttp.deleteJson(url, *formatArgs)
    }

    fun postJsonArray(url: String, vararg formatArgs: Any): RxHttpJsonArrayParam {
        return RxHttp.postJsonArray(url, *formatArgs)
    }

    fun putJsonArray(url: String, vararg formatArgs: Any): RxHttpJsonArrayParam {
        return RxHttp.putJsonArray(url, *formatArgs)
    }

    fun patchJsonArray(url: String, vararg formatArgs: Any): RxHttpJsonArrayParam {
        return RxHttp.patchJsonArray(url, *formatArgs)
    }

    fun deleteJsonArray(url: String, vararg formatArgs: Any): RxHttpJsonArrayParam {
        return RxHttp.deleteJsonArray(url, *formatArgs)
    }


}

suspend inline fun <reified T : Any> RxHttp<*, *>.flow(
    crossinline onStart: () -> Unit,
    crossinline onSuccess: (data: Result<T>) -> Unit,
    crossinline onError: (code: String, msg: String) -> Unit
) {
    toFlowResultResponse<T>()
        .onStart {
            onStart()
        }
        .catch {
            val msg = it.msg
            val code = it.code
            onError(code, msg)
        }
        .collect {
            onSuccess(it)
        }
}

/**
 * 使用flow进行文件下载
 * @receiver RxHttp<*, *>
 * @param destPath String
 * @param onStart Function0<Unit>
 * @param onSuccess Function4<[@kotlin.ParameterName] String?, [@kotlin.ParameterName] Int, [@kotlin.ParameterName] Long, [@kotlin.ParameterName] Long, Unit>
 * @param onError Function2<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
 */
suspend inline fun <reified T : Any> RxHttp<*, *>.downFlow(
    destPath: String,
    crossinline onStart: () -> Unit,
    crossinline onSuccess: (path: String?, process: Int, currentSize: Long, totalSize: Long) -> Unit,
    crossinline onError: (code: String, msg: String) -> Unit
) {
    toFlowProgress(destPath)
        .onStart {
            onStart()
        }
        .catch {
            val msg = it.msg
            val code = it.code
            onError(code, msg)
        }
        .collect {
            //此时这里将收到所有事件，这里的it为ProgressT<String>对象
            val process = it.progress         //已下载进度  0-100
            val currentSize = it.currentSize  //已下载size，单位：byte
            val totalSize = it.totalSize      //要下载的总size  单位：byte
            val path = it.result              //本地存储路径
            onSuccess(path, process, currentSize, totalSize)
        }
}

suspend inline fun <reified T : Any> RxHttp<*, *>.await(): Result<T> {
    return toResultResponse<T>()
        .await()
}

/**
 * RxHttp http = Net.INSTANCE.get("").add("", "");
 * NetKt.rxJavaList(http, Object.class, () -> null, listResult -> null, (code, msg) -> null);
 * @receiver RxHttp<*, *>
 * @param clazz Class<T>
 * @param onStart Function0<Unit>
 * @param onSuccess Function1<[@kotlin.ParameterName] Result<MutableList<T>>, Unit>
 * @param onError Function2<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
 */
fun <T : Any> RxHttp<*, *>.rxJavaList(
    clazz: Class<T>,
    onStart: () -> Unit,
    onSuccess: (data: Result<MutableList<T>>) -> Unit,
    onError: (code: String, msg: String) -> Unit
) {
    asResultResponseList<T>(clazz)
        .doOnSubscribe {
            onStart()
        }
        .subscribe(
            {
                onSuccess(it)
            },
            {
                val msg = it.msg
                val code = it.code
                onError(code, msg)
            }
        )
}

/**
 * RxHttp http = Net.INSTANCE.get("").add("", "");
 * NetKt.rxJava(http, Object.class, () -> null, listResult -> null, (code, msg) -> null);
 * @receiver RxHttp<*, *>
 * @param clazz Class<T>
 * @param onStart Function0<Unit>
 * @param onSuccess Function1<[@kotlin.ParameterName] Result<T>, Unit>
 * @param onError Function2<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
 */
fun <T : Any> RxHttp<*, *>.rxJava(
    clazz: Class<T>,
    onStart: () -> Unit,
    onSuccess: (data: Result<T>) -> Unit,
    onError: (code: String, msg: String) -> Unit
) {
    asResultResponse<T>(clazz)
        .doOnSubscribe {
            onStart()
        }
        .subscribe(
            {
                onSuccess(it)
            },
            {
                val msg = it.msg
                val code = it.code
                onError(code, msg)
            }
        )
}

fun RxHttp<*, *>.downloadRxJava(
    destPath: String,
    onStart: () -> Unit,
    onProgress: (process: Int, currentSize: Long, totalSize: Long) -> Unit,
    onSuccess: (path: String) -> Unit,
    onError: (code: String, msg: String) -> Unit
) {
    asDownload(destPath, AndroidSchedulers.mainThread()) { progress ->
        //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
        val currentProgress = progress.getProgress(); //当前进度 0-100
        val currentSize = progress.getCurrentSize(); //当前已下载的字节大小
        val totalSize = progress.getTotalSize();     //要下载的总字节大小
        onProgress(currentProgress, currentSize, totalSize)
    }
        .doOnSubscribe {
            onStart()
        }.subscribe(
            {
                onSuccess(it)
            }, {
                val msg = it.msg
                val code = it.code
                onError(code, msg)
            }
        )

}

