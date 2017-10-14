package com.adam.gankarch.data.remote.consumer

import io.reactivex.functions.Consumer
import timber.log.Timber

/**
 * @author yu
 * Create on 2017/10/13.
 */
class ErrorConsumer : Consumer<Throwable> {
    override fun accept(t: Throwable) {
        Timber.e(t)
    }
}
