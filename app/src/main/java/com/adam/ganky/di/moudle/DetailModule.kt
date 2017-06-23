package com.adam.ganky.di.moudle

import com.adam.ganky.di.ActivityScope
import com.adam.ganky.mvp.IDetail
import dagger.Module
import dagger.Provides

@Module
class DetailModule(private val view: IDetail.View) {

    @ActivityScope
    @Provides
    internal fun provideDetailView(): IDetail.View = view

}