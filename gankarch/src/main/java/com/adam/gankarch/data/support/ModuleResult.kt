package com.adam.gankarch.data.support


/**
 * 调用数据层的结果封装
 * Created by yu on 2017/10/18.
 */
class ModuleResult<out T>(val data: T?, val error: Throwable? = null) {

    fun isSuccess() = error == null
}