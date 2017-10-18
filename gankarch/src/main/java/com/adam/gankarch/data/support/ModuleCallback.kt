package com.adam.gankarch.data.support


/**
 * Created by yu on 2017/10/17.
 */
interface ModuleCallback<in T> {
    fun onResultBack(result: ModuleResult<T>)
}