package com.adam.gankarch.common.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.Serializable

/**
 * Created by yu on 2017/10/12.
 */

val mainHandler = Handler(Looper.getMainLooper())
val EMPTY_STRING = ""

/**
 * 添加到指定的CompositeDisposable，便于统一取消订阅
 */
fun Disposable.addToLifecycle(disposables: CompositeDisposable) {
    disposables.add(this)
}

fun <T : Activity> Activity.jumpTo(clazz: Class<T>, key: String? = null, value: Any? = null) {
    Intent(this, clazz).apply {
        if (key != null && value != null) {
            when (value) {
                is Parcelable -> putExtra(key, value)
                is Serializable -> putExtra(key, value)
                else -> throw IllegalArgumentException("not support the value type:" + value.javaClass.name)
            }
        }
    }.let {
        startActivity(it)
    }
}

fun <T : Activity> Fragment.jumpTo(clazz: Class<T>, key: String? = null, value: Any? = null) {
    Intent(activity, clazz).apply {
        if (key != null && value != null) {
            when (value) {
                is Parcelable -> putExtra(key, value)
                is Serializable -> putExtra(key, value)
                else -> throw IllegalArgumentException("not support the value type:" + value.javaClass.name)
            }
        }
    }.let {
        startActivity(it)
    }
}

fun onUiThread(task: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        task()
    } else {
        mainHandler.post { task() }
    }
}

fun <T : Fragment> T.withArgument(key: String, value: Any): T {
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