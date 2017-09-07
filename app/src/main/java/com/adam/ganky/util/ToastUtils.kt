package com.adam.ganky.util

import android.os.Looper
import android.text.TextUtils
import android.widget.Toast


/**
 * ToastUtils
 * Created by tanchuanzhi on 2015/5/8.
 */
object ToastUtils {

    @JvmOverloads fun show(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (TextUtils.isEmpty(message))
            return
        val toast = Toast.makeText(AppManager.appContext(), message, duration)
        if (Looper.myLooper() == Looper.getMainLooper()) {
            toast.show()
        } else {
            HandlerUtils.runOnUI { toast.show() }
        }
    }

}
