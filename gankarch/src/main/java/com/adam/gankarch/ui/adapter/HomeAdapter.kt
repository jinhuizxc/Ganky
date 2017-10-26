package com.adam.gankarch.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.adam.gankarch.common.base.BaseFragment
import com.adam.gankarch.ui.fragment.CategoryFragment
import com.adam.gankarch.util.TabType

/**
 * Created by yu on 2017/10/26.
 */
class HomeAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fmList: List<BaseFragment> by lazy {
        listOf(
                CategoryFragment.newInstance("Android"),
                CategoryFragment.newInstance("iOS"),
                CategoryFragment.newInstance("福利")
        )
    }

    override fun getItem(position: Int): Fragment = fmList[position]

    override fun getCount(): Int = fmList.size

    override fun getPageTitle(position: Int): CharSequence = TabType.getPageTitleByPosition(position)
}