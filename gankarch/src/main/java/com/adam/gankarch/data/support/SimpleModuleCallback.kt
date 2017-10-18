package com.adam.gankarch.data.support

import com.blankj.utilcode.util.ToastUtils

/**
 * Created by yu on 2017/10/18.
 */
abstract class SimpleModuleCallback<in T> : ModuleCallback<T> {
    override fun onResultBack(result: ModuleResult<T>) {
        if (result.isSuccess()) {
            doSuccess(result.data!!)
        } else {
            doError(result.error!!)
        }
    }

    abstract fun doSuccess(data: T)

    open fun doError(ex: Throwable) {
        when (ex) {
            is GankException -> ToastUtils.showShort(ex.errorMessage)
            else -> ToastUtils.showShort("网络繁忙，请稍后再试！")
        }
    }

}