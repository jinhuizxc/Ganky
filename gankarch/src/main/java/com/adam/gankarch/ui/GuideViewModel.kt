package com.adam.gankarch.ui

import android.arch.lifecycle.MutableLiveData
import com.adam.gankarch.common.base.BaseViewModel
import com.adam.gankarch.data.GankRepository
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.DataCallback

/**
 * @author yu
 * Create on 2017/10/14.
 */
class GuideViewModel(private val repository: GankRepository) : BaseViewModel() {

    val girl: MutableLiveData<GankEntity> = MutableLiveData()

    init {
        repository.getGuideGirl(object : DataCallback<GankEntity> {
            override fun onSuccess(entity: GankEntity) {
                girl.postValue(entity)
            }
        })
    }
}