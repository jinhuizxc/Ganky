package com.adam.gankarch.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.common.call.SimpleModuleCallback
import com.adam.gankarch.data.GankRepository
import com.adam.gankarch.data.GankRepositoryImpl
import com.adam.gankarch.data.entity.GankEntity

/**
 * @author yu
 * Create on 2017/10/14.
 */
class DetailViewModel : BaseViewModel() {

    val girl: MutableLiveData<GankEntity> = MutableLiveData()

    private val repository: GankRepository by lazy {
        // 通过这个方法获取的代理，调用数据层的call将会自动取消
        getRepositoryDelegate(GankRepository::class.java, GankRepositoryImpl())
    }

    init {
        repository.getGuideGirl()
                .enqueue(SimpleModuleCallback { girl.postValue(it) })
    }

    fun btnCollect(entity: GankEntity) {
        Log.e("qwe", entity.toString())
    }
}