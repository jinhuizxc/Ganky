package com.adam.ganky.util

import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import java.security.MessageDigest


/**
 * 通用工具，各种util
 */
object AppUtils {

    /**
     * 第一次和第二次的退出间隔时间
     */
    private val EXIT_TWICE_INTERVAL: Long = 2000
    private var mExitTime: Long = 0

    /**
     * 第二次按退出则返回true,否则返回false
     */
    fun exitTwice(): Boolean {
        val newExitTime = System.currentTimeMillis()
        return if (newExitTime - mExitTime > EXIT_TWICE_INTERVAL) {
            mExitTime = newExitTime
            false
        } else {
            true
        }
    }

    /**
     * 获取Android设备的唯一id，通过AndroidId，mac地址，IMEI和rom及硬件信息计算一个MD5值
     */
    fun getUniqueId(context: Context): String {
        val before = getAndroidId(context) + getMacAddress(context) + getIMEI(context) + uuid
        var after = ""

        try {
            val m = MessageDigest.getInstance("MD5")
            m.update(before.toByteArray(), 0, before.length)
            val buffer = m.digest()

            for (data in buffer) {
                val b = 0xFF and data.toInt()
                if (b <= 0xF)
                    after += "0"
                after += Integer.toHexString(b)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return after.toUpperCase()
    }

    /**
     * 定位是否打开，gps或agps一个打开就认为打开
     */
    fun isGpsOpen(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || network
    }

    /**
     * 设备sim状态是否正常
     */
    fun isSimReady(context: Context): Boolean {
        val tManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tManager.simState == TelephonyManager.SIM_STATE_READY// sim状态是否正常
    }

    /**
     * 获取Android id
     */
    private fun getAndroidId(context: Context): String =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    /**
     * 获取 MAC 地址
     * 须配置android.permission.ACCESS_WIFI_STATE权限
     */
    private fun getMacAddress(context: Context): String {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        return info.macAddress
    }

    /**
     * 获取IMEI
     * 无通话功能或者水货将无效
     */
    private fun getIMEI(context: Context): String {
        val TelephonyMgr = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        return TelephonyMgr.deviceId
    }

    /**
     * 通过ROM版本、制造商、CPU型号、以及其他硬件信息来计算一个id
     * 当硬件和rom等完全一样时，可能重复（几率小）
     */
    private val uuid: String
        get() = "35" +
                Build.BOARD.length % 10 +
                Build.BRAND.length % 10 +
                Build.CPU_ABI.length % 10 +
                Build.DEVICE.length % 10 +
                Build.DISPLAY.length % 10 +
                Build.HOST.length % 10 +
                Build.ID.length % 10 +
                Build.MANUFACTURER.length % 10 +
                Build.MODEL.length % 10 +
                Build.PRODUCT.length % 10 +
                Build.TAGS.length % 10 +
                Build.TYPE.length % 10 +
                Build.USER.length % 10

}
