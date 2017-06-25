package com.adam.ganky.base


interface IView {
    fun showLoading()

    fun showLoading(message: String?)

    fun hideLoading()

    fun onError()
}
