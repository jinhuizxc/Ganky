package com.adam.gankarch.data.remote

import com.adam.gankarch.data.GankRepository
import com.adam.gankarch.data.api.GankApi
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.GankException
import com.adam.gankarch.data.support.ModuleResult
import com.adam.gankarch.data.support.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 远程DataSource
 * Created by yu on 2017/10/17.
 */
class GankRemoteDataSource : GankRepository {

    private val apiService: GankApi = RetrofitHelper.instance.createService(GankApi::class.java)

    override fun getGuideGirl(callback: (ModuleResult<GankEntity>) -> Unit) {
        getRandomGirl(callback)
    }

    override fun getRandomGirl(callback: (ModuleResult<GankEntity>) -> Unit) {
        apiService.getRandomGirl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess()) {
                        callback(ModuleResult(it.results!![0]))
                    } else {
                        callback(ModuleResult(null, GankException(errorMessage = it.message())))
                    }
                }, { callback(ModuleResult(null, it)) })
    }

    override fun getListData(type: String, pageSize: String, page: String, callback: (ModuleResult<List<GankEntity>>) -> Unit) {
        apiService.gank(type, pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess()) {
                        callback(ModuleResult(it.results))
                    } else {
                        callback(ModuleResult(null, GankException(errorMessage = it.message())))
                    }
                }, { callback(ModuleResult(null, it)) })
    }

}