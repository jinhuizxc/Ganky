package com.adam.ganky.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import com.adam.ganky.R
import com.adam.ganky.base.BaseActivity
import com.adam.ganky.http.ScaleType
import com.adam.ganky.http.displayImage
import com.adam.ganky.jump
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 优化冷启动白屏问题
 * Created by yu on 2017/9/28.
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initView() {
        displayImage {
            activity = this@SplashActivity
            url = "http://ww2.sinaimg.cn/large/610dc034gw1eyrfnh1fcuj20ey0mi3zz.jpg"
            scaleType = ScaleType.CENTER_CROP
            imageView = ivSplash
        }

        ObjectAnimator.ofFloat(ivSplash, "alpha", 0f, 1f)
                .setDuration(2000)
                .apply {
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            jump(MainActivity::class.java)
                            finish()
                        }
                    })
                }.start()
    }
}
