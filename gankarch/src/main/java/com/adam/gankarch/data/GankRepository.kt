package com.adam.gankarch.data

import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.ModuleCallback

/**
 * Created by yu on 2017/10/17.
 */
interface GankRepository {

    fun getGuideGirl(callback: ModuleCallback<GankEntity>)

    fun getRandomGirl(callback: ModuleCallback<GankEntity>)

    fun getListData(type: String, pageSize: String, page: String, callback: ModuleCallback<List<GankEntity>>)
}