package com.adam.gankarch.common.call

/**
 * @author yu
 * Create on 2017/10/19.
 */
interface ModuleCallback<in T> {
    fun onStart()
    fun onResultBack(result: ModuleResult<T>)
    fun onFinal()
}