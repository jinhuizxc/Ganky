package com.adam.ganky.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by yu on 2017/3/10.
 */
abstract class BaseFragment : RxFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
    }

    open fun injectComponent() {}

}