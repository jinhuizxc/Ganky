package com.adam.ganky

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.io.Serializable

/**
 * 扩展函数
 * @author yu
 * Create on 2017/6/23.
 */
fun Context.jump(clz: Class<out Activity>, key: String? = null, value: Serializable? = null) {
    val intent = Intent(this, clz)

    if (null != key && null != value)
        intent.putExtra(key, value)

    if (this !is Activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    startActivity(intent)
}

fun Fragment.jump(clz: Class<out Activity>, key: String? = null, value: Serializable? = null) {
    val intent = Intent(activity, clz)
    if (null != key && null != value)
        intent.putExtra(key, value)
    startActivity(intent)
}