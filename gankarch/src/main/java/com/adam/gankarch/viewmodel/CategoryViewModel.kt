package com.adam.gankarch.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.http.ApiConsumer
import com.adam.gankarch.data.repository.MainRepository
import com.adam.gankarch.data.repository.impl.MainRepositoryImpl

/**
 * @author yu
 * Create on 2017/10/14.
 */
class CategoryViewModel : BaseViewModel() {

    private var page = 1
    private val pageSize = 10

    val dataSet: MutableLiveData<List<GankEntity>> = MutableLiveData()
    val loadMoreData: MutableLiveData<List<GankEntity>> = MutableLiveData()

    private val repository: MainRepository by lazy { MainRepositoryImpl() }

    fun refresh(type: String) {
        page = 1
        load(type, pageSize.toString(), page.toString())
    }

    fun loadMore(type: String) {
        page++
        load(type, pageSize.toString(), page.toString())
    }

    private fun load(type: String, pageSize: String, pageNum: String) {
        repository.getListData(type, pageSize, pageNum)
                .subscribe(ApiConsumer {
                    if (page == 1) {// 刷新
                        dataSet.postValue(it!!)
                    } else { // 加载更多
                        loadMoreData.postValue(it!!)
                    }
                })
    }
}