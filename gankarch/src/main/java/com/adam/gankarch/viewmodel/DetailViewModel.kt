package com.adam.gankarch.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.common.call.SimpleModuleCallback
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.repository.MainRepository
import com.adam.gankarch.data.repository.impl.MainRepositoryImpl

/**
 * @author yu
 * Create on 2017/10/14.
 */
class DetailViewModel : BaseViewModel() {

    val girl = ObservableField<String>()
    val isCollected = ObservableBoolean(false)

    private val repository: MainRepository by lazy {
        // 通过这个方法获取的代理，调用数据层的call将会自动取消
        getRepositoryDelegate(MainRepository::class.java, MainRepositoryImpl())
    }

    init {
        repository.getGuideGirl()
                .enqueue(SimpleModuleCallback { girl.set(it!!.url) })
    }

    fun checkCollected(entity: GankEntity) {
        Log.e("qwe", "_____checkCollected______")
        isCollected.set(true)
    }

    fun btnCollect(entity: GankEntity) {
        Log.e("qwe", entity.toString())
        isCollected.set(!isCollected.get())
    }
}