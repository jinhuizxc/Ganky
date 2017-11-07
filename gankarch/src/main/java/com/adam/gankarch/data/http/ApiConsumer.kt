package com.adam.gankarch.data.http

import com.adam.gankarch.common.base.Resp
import io.reactivex.functions.Consumer

/**
 * Created by yu on 2017/11/7.
 */
open class ApiConsumer<T>(private val success: (data: T?) -> Unit) : Consumer<Resp<T>> {

    override fun accept(t: Resp<T>) {
        if (t.isSuccess()) {
            success(t.data)
        } else {
            doFail(t.error)
        }
    }

    open fun doFail(error: Throwable?) {
        error?.printStackTrace()
    }
}