package com.adam.gankarch.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.http.ApiConsumer
import com.adam.gankarch.data.repository.CollectionRepository
import com.adam.gankarch.data.repository.impl.CollectionRepositoryImpl
import com.adam.gankarch.util.SingleLiveEvent

/**
 * @author yu
 * Create on 2017/10/14.
 */
class CollectionViewModel : BaseViewModel() {

    private var page = 0
    private val pageSize = 10

    val deleteSuccess = SingleLiveEvent<Void>()
    val dataSet: MutableLiveData<List<GankEntity>> = MutableLiveData()
    val loadMoreData: MutableLiveData<List<GankEntity>> = MutableLiveData()

    private val repository: CollectionRepository by lazy { CollectionRepositoryImpl() }

    fun refresh() {
        page = 0
        load(pageSize, page)
    }

    fun loadMore() {
        page++
        load(pageSize, page)
    }

    fun delete(entity: GankEntity) {
        repository.deleteCollection(entity)
        deleteSuccess.call()
    }

    private fun load(pageSize: Int, pageNum: Int) {
        repository.getCollection(pageNum, pageSize)
                .subscribe(ApiConsumer {
                    if (page == 0) {// 刷新
                        dataSet.postValue(it!!)
                    } else { // 加载更多
                        loadMoreData.postValue(it!!)
                    }
                })
                .apply { addDisposables(this) }
    }
}