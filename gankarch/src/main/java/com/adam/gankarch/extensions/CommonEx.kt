package com.adam.gankarch.extensions

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.widget.Toast
import java.io.Serializable

/**
 * Created by yu on 2017/10/12.
 */

val emptyString = ""

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.activity.applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.withArgument(key: String, value: Any): Fragment {
    val hasArguments = arguments != null

    val args = if (hasArguments) {
        arguments
    } else {
        Bundle()
    }.apply {
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Parcelable -> putParcelable(key, value)
            is Serializable -> putSerializable(key, value)
            else -> throw UnsupportedOperationException("${value.javaClass.simpleName} type not supported yet!!!")
        }
    }

    if (!hasArguments) arguments = args

    return this
}

inline fun delay(milliseconds: Long, mainThread: Boolean = true, crossinline action: () -> Unit) {
    if (mainThread) {
        Handler(Looper.getMainLooper())
    } else {
        Handler()
    }.apply {
        postDelayed({
            action()
        }, milliseconds)
    }
}

fun <T> nonSafeLazy(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        initializer()
    }
}