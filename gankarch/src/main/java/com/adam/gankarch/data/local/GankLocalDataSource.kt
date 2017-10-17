package com.adam.gankarch.data.local

import com.adam.gankarch.data.GankRepository
import com.adam.gankarch.data.SpConstants
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.DataCallback
import com.adam.gankarch.data.support.GankException
import com.blankj.utilcode.util.EmptyUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson

/**
 * 本地DataSource
 * Created by yu on 2017/10/17.
 */
class GankLocalDataSource : GankRepository {

    private val gson = Gson()

    override fun getGuideGirl(callback: DataCallback<GankEntity>) {
        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR)
        if (EmptyUtils.isNotEmpty(str)) {
            callback.onSuccess(gson.fromJson(str, GankEntity::class.java))
        } else {
            callback.onFail(GankException())
        }
    }

    override fun getRandomGirl(callback: DataCallback<GankEntity>) {

    }

    override fun getListData(type: String, pageSize: String, page: String, callback: DataCallback<List<GankEntity>>) {

    }

}