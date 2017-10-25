package com.adam.gankarch.common.call

import android.util.Log
import com.adam.gankarch.common.base.BaseRepository
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap


/**
 * 通过这个管理类获取的Repository子类，将自动收集mModuleCalls，用于统一取消请求
 * @author yu
 * Create on 2017/10/19.
 */
class RepositoryDelegate {

    private val mRepositoryCache = ConcurrentHashMap<Class<*>, Any>()
    private val mModuleCalls = ModuleCalls()

    fun <T : BaseRepository> getDelegate(interfaceClz: Class<T>, impl: T): T {
        synchronized(mRepositoryCache) {
            val repository = mRepositoryCache[interfaceClz]
            return if (repository != null) {
                repository as T
            } else {
                val invocationHandler = ModuleInvocationHandler(impl, mModuleCalls)
                Proxy.newProxyInstance(interfaceClz.classLoader,
                        interfaceClz.interfaces, invocationHandler)
                        .apply {
                            mRepositoryCache.put(interfaceClz, this)
                        } as T
            }
        }
    }

    fun cancelAll() {
        mModuleCalls.cancel()
        // 清理缓存的module
        synchronized(mRepositoryCache) {
            mRepositoryCache.clear()
        }
    }

    /**
     * 自动收集返回的ModuleCall
     */
    class ModuleInvocationHandler(private val target: Any?, private val moduleCalls: ModuleCalls) : InvocationHandler {

        override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
            val res = if (args == null)
                method.invoke(target)
            else
                method.invoke(target, args)

            if (ModuleCall::class.java == method.returnType) { // 收集返回的moduleCall
                moduleCalls.add(res as ModuleCall<*>)
            }
            return res
        }
    }

    /**
     * 用于统一取消调用
     */
    class ModuleCalls {
        private var mModuleCalls: MutableList<ModuleCall<*>>? = null

        fun add(call: ModuleCall<*>) {
            if (mModuleCalls == null) {
                synchronized(this) {
                    if (mModuleCalls == null) {
                        mModuleCalls = mutableListOf()
                    }
                }
            }

            if (mModuleCalls!!.size >= 5) { // 及时移除已经完成的调用
                synchronized(this) {
                    val iterator = mModuleCalls!!.iterator()
                    var tmp: ModuleCall<*>
                    while (iterator.hasNext()) {
                        tmp = iterator.next()
                        if (tmp.isDone()) {
                            iterator.remove()
                        }
                    }
                }
            }

            synchronized(this) {
                mModuleCalls!!.add(call)
            }
            Log.i("delegate", "<${this}>增加一个ModuleCall[$call],当前数量:[${mModuleCalls!!.size}]")
        }

        fun cancel() {
            if (mModuleCalls == null) {
                return
            }
            synchronized(this) {
                for (call in mModuleCalls!!) {
                    call.cancel()
                    Log.i("delegate", "<${this}>取消了所有ModuleCall")
                }
                mModuleCalls!!.clear()
            }
        }
    }
}