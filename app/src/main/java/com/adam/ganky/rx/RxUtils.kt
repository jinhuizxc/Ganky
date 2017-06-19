package com.adam.ganky.rx

import com.adam.ganky.base.IView
import com.adam.ganky.http.ApiException
import com.adam.ganky.http.HttpResult
import com.adam.ganky.http.NetworkDisconnectException
import com.adam.ganky.util.NetUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by yu on 2017/6/19.
 */
object RxUtils {
    /**
     * 访问网络通用Transformer，判断了网络，切换线程，剥离结果
     */
    fun <T> apiTransformer(view: IView): ObservableTransformer<HttpResult<T>, T> {
        return ObservableTransformer {
            it.compose(RxUtils.bindToLifecycle<HttpResult<T>>(view))
                    .compose(RxUtils.checkNetwork<HttpResult<T>>())
                    .compose(RxUtils.parseResult<T>())
                    .compose(RxUtils.ioToMain<T>())
        }
    }

    /**
     * 绑定rx订阅的生命周期
     */
    fun <T> bindToLifecycle(view: IView): ObservableTransformer<T, T> {
        return ObservableTransformer {
            if (view is RxAppCompatActivity) {
                it.compose(view.bindToLifecycle<T>())
            } else if (view is RxFragment) {
                it.compose(view.bindToLifecycle<T>())
            } else {
                throw IllegalArgumentException("不支持的IVew类型~~~")
            }
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
     * 检查网络
     */
    fun <T> checkNetwork(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.doOnSubscribe { if (!NetUtils.isOnline) throw NetworkDisconnectException() }
        }
    }

    /**
     * 剥离接口返回的对象
     */
    fun <T> parseResult(): ObservableTransformer<HttpResult<T>, T> {
        return ObservableTransformer {
            it.flatMap {
                if (it.isError) {
                    Observable.error<T>(ApiException("接口返回错误"))
                } else {
                    Observable.just(it.results)
                }
            }
        }
    }

}
