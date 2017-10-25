package com.adam.gankarch.data

import com.adam.gankarch.common.base.BaseRepository
import com.adam.gankarch.common.call.ModuleCall
import com.adam.gankarch.data.entity.GankEntity

/**
 * Created by yu on 2017/10/17.
 */
interface GankRepository : BaseRepository {

    fun getGuideGirl(): ModuleCall<GankEntity>

    fun getRandomGirl(): ModuleCall<GankEntity>

    fun getListData(type: String, pageSize: String, page: String): ModuleCall<List<GankEntity>>
}