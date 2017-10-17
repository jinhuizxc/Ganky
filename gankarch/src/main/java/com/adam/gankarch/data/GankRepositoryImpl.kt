package com.adam.gankarch.data

import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.local.GankLocalDataSource
import com.adam.gankarch.data.remote.GankRemoteDataSource
import com.adam.gankarch.data.support.DataCallback
import com.adam.gankarch.data.support.GankException
import com.blankj.utilcode.util.EmptyUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson

/**
 * Created by yu on 2017/10/17.
 */
class GankRepositoryImpl : GankRepository {

    private val localDataSource = GankLocalDataSource()
    private val remoteDataSource = GankRemoteDataSource()

    override fun getGuideGirl(callback: DataCallback<GankEntity>) {
        val count = SPUtils.getInstance().getInt(SpConstants.GUIDE_GIRL_USED_TIME, 0)
        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR)
        if (count < 7 && EmptyUtils.isNotEmpty(str)) {
            localDataSource.getGuideGirl(callback)
            SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, count + 1)
        } else {
            remoteDataSource.getGuideGirl(object : DataCallback<GankEntity> {
                override fun onSuccess(entity: GankEntity) {
                    callback.onSuccess(entity)
                    SPUtils.getInstance().remove(SpConstants.GUIDE_GIRL_USED_TIME)
                    SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_ENTITY_STR, Gson().toJson(entity))
                }

                override fun onFail(ex: GankException) {
                    callback.onFail(ex)
                }
            })
        }
    }

    override fun getRandomGirl(callback: DataCallback<GankEntity>) {

    }

    override fun getListData(type: String, pageSize: String, page: String, callback: DataCallback<List<GankEntity>>) {

    }
}