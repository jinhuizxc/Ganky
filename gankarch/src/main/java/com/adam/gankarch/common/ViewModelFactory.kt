package com.adam.gankarch.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adam.gankarch.ui.GuideViewModel

/**
 * ViewModelFactory
 */
class ViewModelFactory private constructor() : ViewModelProvider.Factory {

    // 单例
    companion object {
        val instance = ViewModelFactory()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GuideViewModel::class.java)) {
            GuideViewModel() as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}