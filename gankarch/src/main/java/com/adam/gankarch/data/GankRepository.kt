package com.adam.gankarch.data

import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.DataCallback

/**
 * Created by yu on 2017/10/17.
 */
interface GankRepository {

    fun getGuideGirl(callback: DataCallback<GankEntity>)

    fun getRandomGirl(callback: DataCallback<GankEntity>)

    fun getListData(type: String, pageSize: String, page: String, callback: DataCallback<List<GankEntity>>)
}