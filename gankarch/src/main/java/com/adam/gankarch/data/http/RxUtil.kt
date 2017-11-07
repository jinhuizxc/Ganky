package com.adam.gankarch.data.http

import android.databinding.ObservableBoolean
import com.adam.gankarch.common.base.Resp
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by yu on 2017/10/23.
 */
object RxUtil {

    fun <T> httpToResp(): ObservableTransformer<HttpResult<T>, Resp<T>> {
        return ObservableTransformer {
            it.map {
                if (it.isSuccess()) {
                    return@map Resp(it.results)
                } else {
                    return@map Resp(null, GankException(errorMessage = "business error"))
                }
            }
        }
    }

    fun <T> withLoading(showLoading: ObservableBoolean): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.doOnSubscribe { showLoading.set(true) }
                    .subscribeOn(AndroidSchedulers.mainThread())// 指定doOnSubscribe的线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { showLoading.set(false) }
        }
    }

    /**
     * io线程到主线程
     */
    fun <T> ioToMain(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.doOnError { it.printStackTrace() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 订阅和生产都在io线程
     */
    fun <T> ioToIo(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.doOnError { it.printStackTrace() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
        }
    }

}