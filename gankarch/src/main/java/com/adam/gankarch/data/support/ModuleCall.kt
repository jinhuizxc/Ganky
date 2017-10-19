package com.adam.gankarch.data.support

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription


/**
 * @author yu
 * Create on 2017/10/19.
 */
class ModuleCall<T> {

    companion object {
        val TYPE_ERROR = -1
        val TYPE_OBSERVABLE = 0
        val TYPE_FLOWABLE = 1
        val TYPE_SINGLE = 2
        val TYPE_MAYBE = 3
    }

    private var mSourceObservable: Observable<ModuleResult<T>>? = null
    private var mSourceFlowable: Flowable<ModuleResult<T>>? = null
    private var mSourceSingle: Single<ModuleResult<T>>? = null
    private var mSourceMaybe: Maybe<ModuleResult<T>>? = null

    private var mSuorceType = TYPE_ERROR
    private var mDone = false
    private var mCanceled = false
    private var mExecuted = false
    private lateinit var mCancelHandle: Any

    constructor(observable: Observable<ModuleResult<T>>) {
        mSourceObservable = observable
        mSuorceType = TYPE_OBSERVABLE
    }

    constructor(observable: Flowable<ModuleResult<T>>) {
        mSourceFlowable = observable
        mSuorceType = TYPE_FLOWABLE
    }

    constructor(observable: Single<ModuleResult<T>>) {
        mSourceSingle = observable
        mSuorceType = TYPE_SINGLE
    }

    constructor(observable: Maybe<ModuleResult<T>>) {
        mSourceMaybe = observable
        mSuorceType = TYPE_MAYBE
    }

    fun cancel() {
        if (mCancelHandle is Disposable) {
            (mCancelHandle as? Disposable)?.dispose()
        } else if (mCancelHandle is Subscription) {
            (mCancelHandle as? Subscription)?.cancel()
        }
        mCanceled = true
    }

    fun isDone(): Boolean = mDone || mCanceled

    fun isCanceled(): Boolean = mCanceled

    fun enqueue(callback: ModuleCallback<T>) {
        synchronized(this) {
            if (mExecuted) throw IllegalStateException("每个ModuleCall只能enqueue一次")
            mExecuted = true
        }
        if (mCanceled || mDone) return

        when (mSuorceType) {
            TYPE_OBSERVABLE -> subscribeObservable(mSourceObservable!!, callback)
            TYPE_FLOWABLE -> subscribeFlowable(mSourceFlowable!!, callback)
            TYPE_SINGLE -> subscribeSingle(mSourceSingle!!, callback)
            TYPE_MAYBE -> subscribeMaybe(mSourceMaybe!!, callback)
        }

    }

    private fun subscribeMaybe(maybe: Maybe<ModuleResult<T>>, callback: ModuleCallback<T>) {
        maybe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MaybeObserver<ModuleResult<T>> {
                    override fun onSubscribe(d: Disposable) {
                        mCancelHandle = d
                        callback.onStart()
                    }

                    override fun onSuccess(t: ModuleResult<T>) {
                        doCallback(t, callback)
                    }

                    override fun onComplete() {
                        mDone = true
                        callback.onFinal()
                    }

                    override fun onError(e: Throwable) {
                        doCallback(ModuleResult(null, e), callback)
                        mDone = true
                        callback.onFinal()
                    }
                })
    }

    private fun subscribeSingle(single: Single<ModuleResult<T>>, callback: ModuleCallback<T>) {
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<ModuleResult<T>> {
                    override fun onSubscribe(d: Disposable) {
                        mCancelHandle = d
                        callback.onStart()
                    }

                    override fun onSuccess(t: ModuleResult<T>) {
                        doCallback(t, callback)
                        mDone = true
                        callback.onFinal()
                    }

                    override fun onError(e: Throwable) {
                        doCallback(ModuleResult(null, e), callback)
                        mDone = true
                        callback.onFinal()
                    }
                })
    }

    private fun subscribeFlowable(flowable: Flowable<ModuleResult<T>>, callback: ModuleCallback<T>) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : FlowableSubscriber<ModuleResult<T>> {
                    override fun onSubscribe(s: Subscription) {
                        mCancelHandle = s
                        callback.onStart()
                    }

                    override fun onNext(t: ModuleResult<T>) {
                        doCallback(t, callback)
                    }

                    override fun onComplete() {
                        mDone = true
                        callback.onFinal()
                    }

                    override fun onError(e: Throwable) {
                        doCallback(ModuleResult(null, e), callback)
                        mDone = true
                        callback.onFinal()
                    }
                })
    }

    private fun subscribeObservable(observable: Observable<ModuleResult<T>>, callback: ModuleCallback<T>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ModuleResult<T>> {
                    override fun onSubscribe(d: Disposable) {
                        mCancelHandle = d
                        callback.onStart()
                    }

                    override fun onNext(t: ModuleResult<T>) {
                        doCallback(t, callback)
                    }

                    override fun onComplete() {
                        mDone = true
                        callback.onFinal()
                    }

                    override fun onError(e: Throwable) {
                        doCallback(ModuleResult(null, e), callback)
                        mDone = true
                        callback.onFinal()
                    }
                })
    }

    private fun doCallback(result: ModuleResult<T>, callback: ModuleCallback<T>) {
        if (!mCanceled) callback.onResultBack(result)
    }

}
