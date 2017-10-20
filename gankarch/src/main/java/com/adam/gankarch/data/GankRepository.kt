package com.adam.gankarch.data

import com.adam.gankarch.common.base.BaseRepository
import com.adam.gankarch.common.call.ModuleCall
import com.adam.gankarch.common.call.ProxyTarget
import com.adam.gankarch.data.bean.GankEntity

/**
 * Created by yu on 2017/10/17.
 */
@ProxyTarget(GankRepositoryImpl::class)// 使用注解标识，RepositoryDelegate中才能找到对应实现类
interface GankRepository : BaseRepository {

    fun getGuideGirl(): ModuleCall<GankEntity>

    fun getRandomGirl(): ModuleCall<GankEntity>

    fun getListData(type: String, pageSize: String, page: String): ModuleCall<List<GankEntity>>
}