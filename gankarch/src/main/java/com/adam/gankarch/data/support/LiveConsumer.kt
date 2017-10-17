package com.adam.gankarch.data.support

import android.arch.lifecycle.MutableLiveData
import com.adam.gankarch.common.extensions.onUiThread
import io.reactivex.functions.Consumer

/**
 * LiveData
 * @author yu
 * Create on 2017/10/13.
 */
open class LiveConsumer<T>(private val mLiveData: MutableLiveData<T>) : Consumer<T> {

    override fun accept(t: T) {
        onUiThread {
            mLiveData.value = t
        }
    }
}