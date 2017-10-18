package com.adam.gankarch.data.local

import com.adam.gankarch.data.GankRepository
import com.adam.gankarch.data.SpConstants
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.GankException
import com.adam.gankarch.data.support.ModuleCallback
import com.adam.gankarch.data.support.ModuleResult
import com.blankj.utilcode.util.EmptyUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson

/**
 * 本地DataSource
 * Created by yu on 2017/10/17.
 */
class GankLocalDataSource : GankRepository {

    private val gson by lazy { Gson() }

    override fun getGuideGirl(callback: ModuleCallback<GankEntity>) {

        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR)
        val count = SPUtils.getInstance().getInt(SpConstants.GUIDE_GIRL_USED_TIME, 1)
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, count + 1)

        if (EmptyUtils.isNotEmpty(str)) {
            callback.onResultBack(ModuleResult(gson.fromJson(str, GankEntity::class.java)))
        } else {
            callback.onResultBack(ModuleResult(null, GankException(errorMessage = "GuideGirl is not found from cache")))
        }
    }

    fun refreshGuideGirl(entity: GankEntity) {
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, 1)
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_ENTITY_STR, Gson().toJson(entity))
    }


    override fun getRandomGirl(callback: ModuleCallback<GankEntity>) {

    }

    override fun getListData(type: String, pageSize: String, page: String, callback: ModuleCallback<List<GankEntity>>) {

    }

}