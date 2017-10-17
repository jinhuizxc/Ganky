package com.adam.gankarch.ui

import android.databinding.DataBindingUtil
import com.adam.gankarch.R
import com.adam.gankarch.common.base.BaseActivity
import com.adam.gankarch.databinding.ActivityGuideBinding

class GuideActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_guide

    override fun initContentView() {

        val binding = DataBindingUtil.setContentView<ActivityGuideBinding>(this, getLayoutId())

//        val viewModel = ViewModelProviders.of(this, ViewModelFactory.instance).get(GuideViewModel::class.java)
//
//        viewModel.girl.observe(this, Observer<GankEntity> {
//            binding.girl = it
//        })
    }
}
