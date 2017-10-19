package com.adam.gankarch.data

import com.adam.gankarch.common.call.ModuleCall
import com.adam.gankarch.data.bean.GankEntity

/**
 * Created by yu on 2017/10/17.
 */
interface GankRepository {

    fun getGuideGirl(): ModuleCall<GankEntity>

    fun getRandomGirl(): ModuleCall<GankEntity>

    fun getListData(type: String, pageSize: String, page: String): ModuleCall<List<GankEntity>>
}