package com.adam.gankarch.data.repository.impl

import android.text.TextUtils
import com.adam.gankarch.common.base.Resp
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.http.RxUtil
import com.adam.gankarch.data.http.SpConstants
import com.adam.gankarch.data.repository.MainRepository
import com.adam.gankarch.data.source.loacl.MainLocalDataSource
import com.adam.gankarch.data.source.remote.MainRemoteDataSource
import com.blankj.utilcode.util.SPUtils
import io.reactivex.Observable

/**
 * Created by yu on 2017/10/17.
 */
class MainRepositoryImpl : MainRepository {

    private val localDataSource: MainLocalDataSource by lazy { MainLocalDataSource() }
    private val remoteDataSource: MainRemoteDataSource by lazy { MainRemoteDataSource() }

    private fun guideGirlCacheIsDirty(): Boolean {
        // 开始页面的美女图片每使用3次就换一个
        val count = SPUtils.getInstance().getInt(SpConstants.GUIDE_GIRL_USED_TIME, 0)
        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR, "")
        return count >= 3 || TextUtils.isEmpty(str)
    }

    override fun getGuideGirl(): Observable<Resp<GankEntity>> {
        return if (guideGirlCacheIsDirty()) {
            remoteDataSource.getGuideGirl()
                    .doOnNext {
                        // 刷新本地缓存
                        if (it.isSuccess())
                            localDataSource.refreshGuideGirl(it.data!!)
                    }
                    .compose(RxUtil.ioToMain())
        } else {
            localDataSource.getGuideGirl()
                    .compose(RxUtil.ioToMain())
        }
    }

    override fun getRandomGirl(): Observable<Resp<GankEntity>> {
        return remoteDataSource.getRandomGirl()
                .compose(RxUtil.ioToMain())
    }

    override fun getListData(type: String, pageSize: String, page: String): Observable<Resp<List<GankEntity>>> {
        return remoteDataSource.getListData(type, pageSize, page)
                .compose(RxUtil.ioToMain())
    }

}