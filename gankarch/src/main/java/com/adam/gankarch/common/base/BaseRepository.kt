package com.adam.gankarch.common.base

import com.adam.gankarch.data.http.HttpResult
import com.adam.gankarch.data.http.NetworkDisconnectException
import com.adam.gankarch.data.http.RxUtil
import com.blankj.utilcode.util.NetworkUtils
import io.reactivex.Observable

/**
 * Created by yu on 2017/10/18.
 */
interface BaseRepository {
    /**
     * 先检查网络，再请求网络
     */
    fun <T> withCheckNetwork(observable: Observable<HttpResult<T>>): Observable<Resp<T>> {
        return if (NetworkUtils.isConnected()) {
            observable.compose(RxUtil.ioToMain())
                    .compose(RxUtil.httpToResp())
        } else {
            Observable.just<Resp<T>>(Resp<T>(null, NetworkDisconnectException()))
                    .compose(RxUtil.ioToMain())
        }
    }

    fun <T> backgroundRequest(observable: Observable<HttpResult<T>>): Observable<Resp<T>> {
        return observable.compose(RxUtil.ioToMain())
                .compose(RxUtil.httpToResp())
    }
}