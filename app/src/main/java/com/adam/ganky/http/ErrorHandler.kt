package com.adam.ganky.http

import com.adam.ganky.util.ToastUtils
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by yu on 2017/5/9.
 */
object ErrorHandler {

    fun handleException(e: Throwable?, showToast: Boolean = true) {
        e?.printStackTrace()
        val exception = parseException(e)
        if (showToast)
            ToastUtils.show(exception?.message)
    }

    private fun parseException(e: Throwable?): ApiException? {
        return when (e) {
            is ApiException -> e
            is SocketTimeoutException, is ConnectException,
            is UnknownHostException, is HttpException -> ApiException("网络不畅，请稍后再试")
            is JSONException, is ParseException, is JsonParseException -> ApiException("数据解析错误")

            else -> ApiException("服务器繁忙")
        }
    }

}
