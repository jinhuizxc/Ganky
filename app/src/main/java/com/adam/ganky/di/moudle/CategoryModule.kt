package com.adam.ganky.di.moudle

import com.adam.ganky.di.ActivityScope
import com.adam.ganky.mvp.ICategory
import dagger.Module
import dagger.Provides

@Module
class CategoryModule(private val mCategoryView: ICategory.View) {

    @ActivityScope
    @Provides
    internal fun provideCategoryView(): ICategory.View = mCategoryView

}