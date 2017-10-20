package com.adam.gankarch.common.call

import com.adam.gankarch.common.base.BaseRepository
import kotlin.reflect.KClass

/**
 * 使用此注解标记Repository接口对应的子类，例如：GankRepository的实现类GankRepositoryImpl
 * 在RepositoryDelegate才能找到对应的实现类
 * @author yu
 * Create on 2017/10/20.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ProxyTarget(val implClz: KClass<out BaseRepository>)