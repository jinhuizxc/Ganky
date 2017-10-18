package com.adam.gankarch.data

import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.local.GankLocalDataSource
import com.adam.gankarch.data.remote.GankRemoteDataSource
import com.adam.gankarch.data.support.ModuleCallback
import com.adam.gankarch.data.support.ModuleResult
import com.blankj.utilcode.util.EmptyUtils
import com.blankj.utilcode.util.SPUtils

/**
 * Created by yu on 2017/10/17.
 */
class GankRepositoryImpl : GankRepository {

    private val localDataSource: GankLocalDataSource by lazy { GankLocalDataSource() }
    private val remoteDataSource: GankRemoteDataSource by lazy { GankRemoteDataSource() }

    override fun getGuideGirl(callback: ModuleCallback<GankEntity>) {
        if (guideGirlCacheIsDirty()) {
            remoteDataSource.getGuideGirl(object : ModuleCallback<GankEntity> {
                override fun onResultBack(result: ModuleResult<GankEntity>) {
                    callback.onResultBack(result)
                    if (result.isSuccess()) {
                        localDataSource.refreshGuideGirl(result.data!!)
                    }
                }
            })
        } else {
            localDataSource.getGuideGirl(callback)
        }
    }

    private fun guideGirlCacheIsDirty(): Boolean {
        // 开始页面的美女图片每使用3次就换一个
        val count = SPUtils.getInstance().getInt(SpConstants.GUIDE_GIRL_USED_TIME, 0)
        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR)
        return count >= 3 || EmptyUtils.isEmpty(str)
    }

    override fun getRandomGirl(callback: ModuleCallback<GankEntity>) {

    }

    override fun getListData(type: String, pageSize: String, page: String, callback: ModuleCallback<List<GankEntity>>) {

    }
}