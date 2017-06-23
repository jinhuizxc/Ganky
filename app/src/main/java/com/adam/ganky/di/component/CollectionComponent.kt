package com.adam.ganky.di.component

import com.adam.ganky.di.ActivityScope
import com.adam.ganky.di.moudle.CollectionModule
import com.adam.ganky.ui.activity.CollectionActivity
import dagger.Component

/**
 * Created by yu on 2017/6/20.
 */
@ActivityScope
@Component(modules = arrayOf(CollectionModule::class), dependencies = arrayOf(AppComponent::class))
interface CollectionComponent {
    fun inject(aty: CollectionActivity)
}