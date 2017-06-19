package com.adam.ganky.base


interface IView {

    fun showLoading(message: String)

    fun hideLoading()

    fun showTips(message: CharSequence)
}
