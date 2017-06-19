package com.adam.ganky.http

import com.adam.ganky.util.ToastUtils

/**
 * Created by yu on 2017/5/9.
 */
object ErrorHandler {

    fun handleException(e: Throwable, showToast: Boolean): ApiException? {
        val exception = parseException(e)
        if (showToast) ToastUtils.show(exception?.message)

        return exception
    }


    private fun parseException(e: Throwable): ApiException? {

        return null
    }
}
