package com.adam.ganky.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.util.*

/**
 * @author yu
 * Create on 2017/6/23.
 */
object AppManager {

    private const val LOCK = "lock"

    private var sContext: Context? = null
    private var activeCount = 0
    private val STACK = LinkedList<Activity>()

    fun init(application: Application) {
        sContext = application
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                this@AppManager.add(activity)
            }

            override fun onActivityStarted(activity: Activity?) {
                synchronized(LOCK) {
                    activeCount++
                }
            }

            override fun onActivityResumed(activity: Activity?) {}

            override fun onActivityPaused(activity: Activity?) {}

            override fun onActivityStopped(activity: Activity?) {
                synchronized(LOCK) {
                    activeCount--
                }
            }

            override fun onActivityDestroyed(activity: Activity?) {
                this@AppManager.remove(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
        })
    }

    fun isFroground(): Boolean = activeCount > 0
    fun appContext(): Context = sContext!!

    // 入栈
    private fun add(aty: Activity?) {
        synchronized(LOCK) {
            STACK.addLast(aty)
        }
    }

    // 出栈
    private fun remove(aty: Activity?) {
        synchronized(LOCK) {
            if (STACK.contains(aty))
                STACK.remove(aty)
        }
    }

    fun getCurrentActivity(): Activity? {
        synchronized(LOCK) {
            return STACK.last
        }
    }

    fun isExists(clazz: Class<out Activity>): Boolean {
        synchronized(LOCK) {
            for (aty in STACK) {
                if (aty.javaClass.name == clazz.name) return true
            }
            return false
        }
    }

    fun exitApp() {
        synchronized(LOCK) {
            val copy = LinkedList(STACK)
            for (aty in copy) {
                aty.finish()
            }
            copy.clear()

            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

    fun finishExcept(clazz: Class<out Activity>) {
        synchronized(LOCK) {
            val copy = LinkedList(STACK)
            for (aty in copy) {
                if (aty.javaClass != clazz) aty.finish()
            }
            copy.clear()
        }
    }

}