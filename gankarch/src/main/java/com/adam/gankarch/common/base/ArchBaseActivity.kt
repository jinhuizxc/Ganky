package com.adam.gankarch.common.base

/**
 * 集成Android Architecture Components的基类
 * Created by yu on 2017/10/13.
 */
abstract class ArchBaseActivity
//    : BaseActivity() {
//
////    lateinit var binding: T
//
//    override fun initContentView() {
////        binding = DataBindingUtil.setContentView(this, getLayoutId())
//        registerLifecycle(lifecycle)
//    }
//
//    open fun registerLifecycle(lifecycle: Lifecycle) {
//        //    这里新增生命周期的观察者
//        //    lifecycle.addObserver(lifecycleObserver)
//    }
//
//    fun <V : ViewModel> getViewModel(clazz: Class<V>): V =
//            ViewModelProviders.of(this, ViewModelFactory.instance).get(clazz)
//}