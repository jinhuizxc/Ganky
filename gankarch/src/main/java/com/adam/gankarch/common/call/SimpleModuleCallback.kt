package com.adam.gankarch.common.call

import com.adam.gankarch.data.support.GankException
import org.json.JSONException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * @author yu
 * Create on 2017/10/19.
 */
open class SimpleModuleCallback<in T>(private val success: (T?) -> Unit) : ModuleCallback<T> {

    override fun onStart() {

    }

    override fun onResultBack(result: ModuleResult<T>) {
        if (result.isSuccess()) {
            success(result.data)
        } else {
            onFail(result.error)
        }
    }

    open fun onFail(ex: Throwable?) {
        val message: String = when (ex) {
            is GankException -> ex.errorMessage
            is ConnectException -> "Connect Fail"
            is UnknownHostException -> "Unknown Host"
            is TimeoutException, is SocketTimeoutException,
            is InterruptedIOException -> "Time out"
            is JSONException -> "Json error"
            else -> "网络繁忙，请稍后再试！"
        }
    }

    override fun onFinal() {

    }
}