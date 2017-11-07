package com.adam.gankarch.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.http.ApiConsumer
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

    private val repository: MainRepository by lazy { MainRepositoryImpl() }

    private val collectionRepository: CollectionRepository by lazy { CollectionRepositoryImpl() }

    fun getRandomGirl() {
        repository.getGuideGirl()
                .subscribe(ApiConsumer { girl.set(it!!.url) })
    }

    fun checkCollected(entity: GankEntity) {
        collectionRepository.isCollected(entity)
                .subscribe { isCollected.set(it) }
    }

    fun btnCollect(entity: GankEntity) {
        if (isCollected.get()) {
            collectionRepository.deleteCollection(entity)
                    .subscribe { isCollected.set(false) }
        } else {
            collectionRepository.addCollection(entity)
                    .subscribe { isCollected.set(true) }
        }
    }
}