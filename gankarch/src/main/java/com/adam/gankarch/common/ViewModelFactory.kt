package com.adam.gankarch.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * ViewModelFactory
 */
class ViewModelFactory : ViewModelProvider.Factory {

    // 单例
    companion object {
        val instance = ViewModelFactory()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if (modelClass.isAssignableFrom(XxxViewModel::class.java)) {
//            XxxViewModel() as T
//        } else {
//            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//        }
        TODO()
    }
}