package com.adam.ganky.util

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager


/**
 * 手机网络相关的工具类
 */
object NetUtils {

    /**
     * 定义网络类型的枚举分类
     * 这里把一些一些2G,2.5G,2.7G等等按照快慢又做了一个分类,仅供参考
     */
    enum class NetworkType {
        WIRED_FAST, WIFI_FAST, MOBILE_FAST, MOBILE_MIDDLE, MOBILE_SLOW, NONE
    }

    /**
     * @return 是否网络在线
     */
    val isOnline: Boolean
        get() {
            val manager = AppManager.appContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            return networkInfo != null && networkInfo.isAvailable
        }

    /**
     * @return 当期的网络类型
     */
    fun type(): NetworkType {
        val manager = AppManager.appContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo

        if (info == null || !info.isConnected) {
            return NetworkType.NONE
        }

        val type = info.type
        val subType = info.subtype


        if (type == ConnectivityManager.TYPE_ETHERNET) {
            return NetworkType.WIRED_FAST
        }

        if (type == ConnectivityManager.TYPE_WIFI) {
            return NetworkType.WIFI_FAST
        }

        if (type == ConnectivityManager.TYPE_MOBILE) {
            when (subType) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> return NetworkType.MOBILE_SLOW // 2G

                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> return NetworkType.MOBILE_MIDDLE// 3G

                TelephonyManager.NETWORK_TYPE_LTE -> return NetworkType.MOBILE_FAST // 4G
            }
        }

        return NetworkType.NONE
    }

}
