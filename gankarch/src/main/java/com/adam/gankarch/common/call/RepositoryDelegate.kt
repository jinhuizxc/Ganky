package com.adam.gankarch.common.call

import com.adam.gankarch.common.base.BaseRepository
import com.blankj.utilcode.util.LogUtils
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*
import java.util.concurrent.ConcurrentHashMap


/**
 * 通过这个管理类获取的BaseRepository子类，将自动收集mModuleCalls，用于统一取消请求
 * @author yu
 * Create on 2017/10/19.
 */
class RepositoryDelegate {

    private val mRepositoryCache = ConcurrentHashMap<Class<*>, BaseRepository>()
    private val mModuleCalls = ModuleCalls()

    fun <T : BaseRepository> getRepository(clazz: Class<T>): T {
        synchronized(mRepositoryCache) {
            var repository = mRepositoryCache[clazz]
            return if (repository != null) {
                repository as T
            } else {
                val invocationHandler = ModuleInvocationHandler(clazz.newInstance(), mModuleCalls)
                val t: T = Proxy.newProxyInstance(clazz.classLoader, clazz.interfaces, invocationHandler) as T
                mRepositoryCache.put(clazz, t)

                t
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
    class ModuleInvocationHandler(private val target: Any, private val moduleCalls: ModuleCalls) : InvocationHandler {

        override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
            val res = method.invoke(target, args)
            if (ModuleCall.javaClass == method.returnType) { // 收集返回的moduleCall
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
                        mModuleCalls = LinkedList()
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
                LogUtils.i("###增加一个ModuleCall###")
            }
        }

        fun cancel() {
            if (mModuleCalls == null) {
                return
            }
            synchronized(this) {
                for (call in mModuleCalls!!) {
                    call.cancel()
                    LogUtils.i("@@@取消了一个ModuleCall@@@")
                }
                mModuleCalls!!.clear()
            }
        }
    }
}