package com.adam.gankarch.data

import com.adam.gankarch.common.base.BaseRepository
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.ModuleResult

/**
 * Created by yu on 2017/10/17.
 */
interface GankRepository : BaseRepository {

    fun getGuideGirl(callback: (ModuleResult<GankEntity>) -> Unit)

    fun getRandomGirl(callback: (ModuleResult<GankEntity>) -> Unit)

    fun getListData(type: String, pageSize: String, page: String,
                    callback: (ModuleResult<List<GankEntity>>) -> Unit)
}