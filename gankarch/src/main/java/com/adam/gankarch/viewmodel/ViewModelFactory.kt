package com.adam.gankarch.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * ViewModelFactory
 */
class ViewModelFactory private constructor() : ViewModelProvider.Factory {

    // 单例
    companion object {
        val instance = ViewModelFactory()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
            when {
                modelClass.isAssignableFrom(GuideViewModel::class.java) -> GuideViewModel() as T
                modelClass.isAssignableFrom(CategoryViewModel::class.java) -> CategoryViewModel() as T
                modelClass.isAssignableFrom(CollectionViewModel::class.java) -> CollectionViewModel() as T
                modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel() as T
                else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
}