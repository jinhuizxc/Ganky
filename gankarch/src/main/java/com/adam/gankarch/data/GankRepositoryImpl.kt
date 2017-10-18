package com.adam.gankarch.data

import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.local.GankLocalDataSource
import com.adam.gankarch.data.remote.GankRemoteDataSource
import com.adam.gankarch.data.support.ModuleResult
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

    override fun getGuideGirl(callback: (ModuleResult<GankEntity>) -> Unit) {
        if (guideGirlCacheIsDirty()) {
            remoteDataSource.getGuideGirl {
                callback(it)
                if (it.isSuccess())
                    localDataSource.refreshGuideGirl(it.data!!)
            }
        } else {
            localDataSource.getGuideGirl(callback)
        }
    }

    override fun getRandomGirl(callback: (ModuleResult<GankEntity>) -> Unit) {
        remoteDataSource.getRandomGirl(callback)
    }

    override fun getListData(type: String, pageSize: String, page: String,
                             callback: (ModuleResult<List<GankEntity>>) -> Unit) {
        remoteDataSource.getListData(type, pageSize, page, callback)

    }
}