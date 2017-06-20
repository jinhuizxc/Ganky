package com.adam.ganky.rx

/**
 * Presenter中绑定生命周期时，如果IView为空，抛出此异常，在订阅者中立即取消订阅；
 * @see RxUtils.bindToLifecycle
 * Created by yu on 2017/5/9.
 */
class LifecycleIsNullException(msg: String) : Exception(msg)