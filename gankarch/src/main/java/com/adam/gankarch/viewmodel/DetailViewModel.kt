package com.adam.gankarch.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.common.call.SimpleModuleCallback
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.repository.CollectionRepository
import com.adam.gankarch.data.repository.MainRepository
import com.adam.gankarch.data.repository.impl.CollectionRepositoryImpl
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

    private val collectionRepository: CollectionRepository by lazy {
        getRepositoryDelegate(CollectionRepository::class.java, CollectionRepositoryImpl())
    }

    fun getRandomGirl() {
        repository.getGuideGirl()
                .enqueue(SimpleModuleCallback { girl.set(it!!.url) })
    }

    fun checkCollected(entity: GankEntity) {
        collectionRepository.isCollected(entity)
                .enqueue(SimpleModuleCallback { isCollected.set(it!!) })
    }

    fun btnCollect(entity: GankEntity) {
        if (isCollected.get()) {
            collectionRepository.deleteCollection(entity)
                    .enqueue(SimpleModuleCallback { isCollected.set(false) })
        } else {
            collectionRepository.addCollection(entity)
                    .enqueue(SimpleModuleCallback { isCollected.set(true) })
        }
    }
}