package com.adam.gankarch.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.common.call.SimpleModuleCallback
import com.adam.gankarch.data.GankRepository
import com.adam.gankarch.data.bean.GankEntity

/**
 * @author yu
 * Create on 2017/10/14.
 */
class GuideViewModel : BaseViewModel() {

    val girl: MutableLiveData<GankEntity> = MutableLiveData()

    private val repository: GankRepository by lazy {
        getRepository(GankRepository::class.java)
    }

    init {
        repository.getGuideGirl()
                .enqueue(SimpleModuleCallback { girl.postValue(it) })
    }
}