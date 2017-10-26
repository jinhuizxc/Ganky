package com.adam.gankarch.data.http

import com.adam.gankarch.common.call.ModuleResult
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Created by yu on 2017/10/23.
 */
object RxUtil {

    fun <T> parseResult(): Function<BaseResponse<T>, Observable<ModuleResult<T>>> {
        return Function {
            val response = it
            Observable.create<ModuleResult<T>> {
                try {
                    val result = if (response.isSuccess()) {
                        ModuleResult(response.results)
                    } else {
                        ModuleResult(null, GankException(errorMessage = response.message()))
                    }
                    it.onNext(result)
                    it.onComplete()
                } catch (e: Throwable) {
                    it.onError(e)
                }
            }
        }
    }

}