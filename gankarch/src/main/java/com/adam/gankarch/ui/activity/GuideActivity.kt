package com.adam.gankarch.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.adam.gankarch.R
import com.adam.gankarch.common.base.ArchBaseActivity
import com.adam.gankarch.common.extensions.jumpTo
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.databinding.ActivityGuideBinding
import com.adam.gankarch.viewmodel.GuideViewModel
import com.blankj.utilcode.util.BarUtils

class GuideActivity : ArchBaseActivity<ActivityGuideBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_guide

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BarUtils.setStatusBarAlpha(this, 0)

        val viewModel = createViewModel(GuideViewModel::class.java)
        viewModel.girl.observe(this, Observer<GankEntity> { mBinding.girl = it })

        ObjectAnimator.ofFloat(mBinding.ivSplash, "alpha", 0f, 1f)
                .setDuration(2500)
                .apply {
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            jumpTo(MainActivity::class.java)
                            finish()
                        }
                    })
                }.start()
    }

}
