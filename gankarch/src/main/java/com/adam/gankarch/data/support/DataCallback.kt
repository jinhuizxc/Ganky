package com.adam.gankarch.data.support


/**
 * Created by yu on 2017/10/17.
 */
interface DataCallback<in T> {
    fun onSuccess(entity: T)
    fun onFail(ex: GankException){}
}