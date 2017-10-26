package com.adam.gankarch.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.common.call.SimpleModuleCallback
import com.adam.gankarch.data.GankRepository
import com.adam.gankarch.data.GankRepositoryImpl
import com.adam.gankarch.data.entity.GankEntity

/**
 * @author yu
 * Create on 2017/10/14.
 */
class CategoryViewModel : BaseViewModel() {

    private var page = 1
    private val pageSize = 10

    val dataSet: MutableLiveData<List<GankEntity>> = MutableLiveData()
    val loadMoreData: MutableLiveData<List<GankEntity>> = MutableLiveData()

    private val repository: GankRepository by lazy {
        // 通过这个方法获取的代理，调用数据层的call将会自动取消
        getRepositoryDelegate(GankRepository::class.java, GankRepositoryImpl())
    }

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
                .enqueue(SimpleModuleCallback {
                    if (page == 1) {// 刷新
                        dataSet.postValue(it!!)
                    } else { // 加载更多
                        loadMoreData.postValue(it!!)
                    }
                })
    }
}