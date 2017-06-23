package com.adam.ganky.di.component

import com.adam.ganky.di.ActivityScope
import com.adam.ganky.di.moudle.CategoryModule
import com.adam.ganky.ui.CategoryFragment
import dagger.Component

/**
 * Created by yu on 2017/6/20.
 */
@ActivityScope
@Component(modules = arrayOf(CategoryModule::class), dependencies = arrayOf(AppComponent::class))
interface CategoryComponent {
    fun inject(fmt: CategoryFragment)
}