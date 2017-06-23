package com.adam.ganky.di.moudle

import com.adam.ganky.di.ActivityScope
import com.adam.ganky.mvp.ICollection
import dagger.Module
import dagger.Provides

@Module
class CollectionModule(private val view: ICollection.View) {

    @ActivityScope
    @Provides
    internal fun provideCollectionView(): ICollection.View = view

}