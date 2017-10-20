package com.adam.gankarch.data

import com.adam.gankarch.common.call.ModuleCall
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.datasource.GankLocalDataSource
import com.adam.gankarch.data.datasource.GankRemoteDataSource
import com.adam.gankarch.data.support.SpConstants
import com.blankj.utilcode.util.EmptyUtils
import com.blankj.utilcode.util.SPUtils

/**
 * Created by yu on 2017/10/17.
 */
class GankRepositoryImpl : GankRepository {

    private val localDataSource: GankLocalDataSource by lazy { GankLocalDataSource() }
    private val remoteDataSource: GankRemoteDataSource by lazy { GankRemoteDataSource() }

    private fun guideGirlCacheIsDirty(): Boolean {
        // 开始页面的美女图片每使用3次就换一个
        val count = SPUtils.getInstance().getInt(SpConstants.GUIDE_GIRL_USED_TIME, 0)
        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR)
        return count >= 3 || EmptyUtils.isEmpty(str)
    }

    override fun getGuideGirl(): ModuleCall<GankEntity> {
        val result = if (guideGirlCacheIsDirty()) {
            remoteDataSource.getGuideGirl()
                    .doOnNext {
                        // 刷新本地缓存
                        if (it.isSuccess()) localDataSource.refreshGuideGirl(it.data!!)
                    }
        } else {
            localDataSource.getGuideGirl()
        }
        return ModuleCall(result)
    }

    override fun getRandomGirl(): ModuleCall<GankEntity> {
        return ModuleCall(remoteDataSource.getRandomGirl())
    }

    override fun getListData(type: String, pageSize: String, page: String)
            : ModuleCall<List<GankEntity>> {
        return ModuleCall(remoteDataSource.getListData(type, pageSize, page))
    }

}