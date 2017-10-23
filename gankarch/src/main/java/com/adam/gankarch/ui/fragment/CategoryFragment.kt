package com.adam.gankarch.ui.fragment

import android.graphics.Color
import com.adam.gankarch.R
import com.adam.gankarch.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_category.*
import java.util.*

/**
 * Created by yu on 2017/10/23.
 */
class CategoryFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_category

    override fun initView() {
        val random = Random()
        recyclerView.setBackgroundColor(
                Color.rgb(44 + random.nextInt(200),
                        44 + random.nextInt(200),
                        44 + random.nextInt(200))
        )
    }

    override fun initData() {

    }
}