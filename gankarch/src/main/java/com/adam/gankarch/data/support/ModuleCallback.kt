package com.adam.gankarch.data.support

/**
 * @author yu
 * Create on 2017/10/19.
 */
interface ModuleCallback<in T> {
    fun onStart()
    fun onResultBack(result: ModuleResult<T>)
    fun onFinal()
}