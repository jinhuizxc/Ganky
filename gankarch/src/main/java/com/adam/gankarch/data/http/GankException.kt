package com.adam.gankarch.data.http

/**
 * Created by yu on 2017/10/17.
 */
class GankException(val code: Int = CODE_DEFAULT, val errorMessage: String = "") : Exception() {

    companion object {
        val CODE_DEFAULT = 0
    }
}