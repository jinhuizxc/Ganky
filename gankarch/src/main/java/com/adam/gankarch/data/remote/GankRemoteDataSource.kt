package com.adam.gankarch.data.remote

import com.adam.gankarch.data.GankRepository
import com.adam.gankarch.data.api.GankApi
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.DataCallback
import com.adam.gankarch.data.support.GankException
import com.adam.gankarch.data.support.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 远程DataSource
 * Created by yu on 2017/10/17.
 */
class GankRemoteDataSource : GankRepository {

    private val apiService: GankApi = RetrofitHelper.instance.createService(GankApi::class.java)

    override fun getGuideGirl(callback: DataCallback<GankEntity>) {
        getRandomGirl(callback)
    }

    override fun getRandomGirl(callback: DataCallback<GankEntity>) {
        apiService.getRandomGirl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess()) {
                        callback.onSuccess(it.results!![0])
                    } else {
                        callback.onFail(GankException())
                    }
                }, {
                    callback.onFail(GankException(GankException.CODE_HTTP_EX, it.message ?: it.toString()))
                })
    }

    override fun getListData(type: String, pageSize: String, page: String, callback: DataCallback<List<GankEntity>>) {
        apiService.gank(type, pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess()) {
                        callback.onSuccess(it.results!!)
                    } else {
                        callback.onFail(GankException())
                    }
                }, {
                    callback.onFail(GankException(GankException.CODE_HTTP_EX, it.message ?: it.toString()))
                })
    }

}