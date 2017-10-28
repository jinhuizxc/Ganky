package com.adam.gankarch.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.common.call.SimpleModuleCallback
import com.adam.gankarch.data.repository.MainRepository
import com.adam.gankarch.data.repository.impl.MainRepositoryImpl
import com.adam.gankarch.data.entity.GankEntity

/**
 * @author yu
 * Create on 2017/10/14.
 */
class GuideViewModel : BaseViewModel() {

    val girl: MutableLiveData<GankEntity> = MutableLiveData()

    private val repository: MainRepository by lazy {
        // 通过这个方法获取的代理，调用数据层的call将会自动取消
        getRepositoryDelegate(MainRepository::class.java, MainRepositoryImpl())
    }

    init {
        repository.getGuideGirl()
                .enqueue(SimpleModuleCallback { girl.postValue(it) })
    }
}