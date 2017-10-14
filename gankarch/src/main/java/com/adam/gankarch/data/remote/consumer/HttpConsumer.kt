package com.adam.gankarch.data.remote.consumer

import android.arch.lifecycle.MutableLiveData
import com.adam.gankarch.common.extensions.onUiThread
import com.adam.gankarch.data.remote.BaseResponse
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.functions.Consumer

/**
 * 专用于链接接口返回的BaseResponse和LiveData
 * @author yu
 * Create on 2017/10/13.
 */
open class HttpConsumer<T>(private val mLiveData: MutableLiveData<T>) : Consumer<BaseResponse<T>> {

    override fun accept(data: BaseResponse<T>) {
        onUiThread {
            if (data.isSuccess()) {
                mLiveData.postValue(data.results)
            } else {
                onBusinessFail(data)
            }
        }
    }

    open fun onBusinessFail(data: BaseResponse<T>) {
        // 这里处理业务失败的情况，取出message提示或者根据code处理
        ToastUtils.showShort("服务器繁忙，请稍后再试...")
    }
}