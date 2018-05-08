package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adam.gankarch.viewmodel.ViewModelFactory

/**
 * 懒加载的fragment
 * 可以通过[.refreshEveryTime]方法来控制是只在第一次可见时加载数据还是每次可见刷新
 * Created by yu on 2017/6/19.
 */
abstract class BaseLazyFragment : Fragment() {

    private var isVisible2User: Boolean = false
    private var isPrepared: Boolean = false
    private var isFirstLoad = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        isFirstLoad = true
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
        lazyLoad()
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     * @param isVisibleToUser 页面是否可见
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisible2User = true
            onVisible()
        } else {
            isVisible2User = false
            onInvisible()
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     * @param hidden 是否隐藏
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            isVisible2User = true
            onVisible()
        } else {
            isVisible2User = false
            onInvisible()
        }
    }

    open fun onVisible() {
        lazyLoad()
    }

    open fun onInvisible() {}

    open fun lazyLoad() {
        if (isPrepared && isVisible2User && isFirstLoad) {
            if (!refreshEveryTime) {
                isFirstLoad = false
            }
            initData()
        }
    }

    /**
     * 这个方法表示是否需要每次回到页面都刷新一次数据
     * @return true 每次刷新    false 只有第一次显示页面时自动加载一次数据
     */
    open val refreshEveryTime: Boolean
        get() = false

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

    fun <V : ViewModel> getViewModel(clazz: Class<V>): V =
            ViewModelProviders.of(this, ViewModelFactory.instance).get(clazz)
}