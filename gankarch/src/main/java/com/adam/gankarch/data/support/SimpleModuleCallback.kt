package com.adam.gankarch.data.support

import com.blankj.utilcode.util.ToastUtils

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
        when (ex) {
            is GankException -> ToastUtils.showShort(ex.errorMessage)
            else -> ToastUtils.showShort("网络繁忙，请稍后再试！")
        }
    }

    override fun onFinal() {

    }
}