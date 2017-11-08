package com.adam.gankarch.viewmodel

import android.databinding.ObservableField
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.common.extensions.addToLifecycle
import com.adam.gankarch.data.http.ApiConsumer
import com.adam.gankarch.data.repository.MainRepository
import com.adam.gankarch.data.repository.impl.MainRepositoryImpl

/**
 * @author yu
 * Create on 2017/10/14.
 */
class GuideViewModel : BaseViewModel() {

    val girl = ObservableField<String>()

    private val repository: MainRepository by lazy { MainRepositoryImpl() }

    init {
        repository.getGuideGirl()
                .subscribe(ApiConsumer { girl.set(it!!.url) })
                .addToLifecycle(mDisposables)
    }
}