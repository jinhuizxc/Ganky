package com.adam.ganky.rx

import com.adam.ganky.base.IView
import com.adam.ganky.http.ApiException
import com.adam.ganky.http.HttpResult
import com.adam.ganky.util.NetUtils
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by yu on 2017/6/19.
 */
object RxUtils {

    /**
     * 剥离data，显示progressBar，判断网络，切换线程
     */
    fun <T> defaultTransformer(view: IView?): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        view?.showLoading()
                        if (!NetUtils.isOnline) throw ApiException("网络无链接，请检查网络")
                    }
                    .subscribeOn(AndroidSchedulers.mainThread())// 指定doOnSubscribe的线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { view?.hideLoading() }
        }
    }

    /**
     * io线程到主线程
     */
    fun <T> ioToMain(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 发射和订阅都在io线程
     */
    fun <T> ioToIo(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
        }
    }

    /**
     * 剥离接口返回的对象
     */
    fun <T> parseResult(): ObservableTransformer<HttpResult<T>, T> {
        return ObservableTransformer {
            it.flatMap {
                if (it.error) {
                    Observable.error<T>(ApiException("接口返回错误"))
                } else {
                    Observable.just(it.results)
                }
            }
        }
    }

}
