package com.adam.ganky.di.component

import com.adam.ganky.di.ActivityScope
import com.adam.ganky.di.moudle.DetailModule
import com.adam.ganky.ui.activity.DetailActivity
import dagger.Component

/**
 * Created by yu on 2017/6/20.
 */
@ActivityScope
@Component(modules = arrayOf(DetailModule::class), dependencies = arrayOf(AppComponent::class))
interface DetailComponent {
    fun inject(aty: DetailActivity)
}