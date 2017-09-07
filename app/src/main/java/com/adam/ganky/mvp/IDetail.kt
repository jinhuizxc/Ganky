package com.adam.ganky.mvp

import com.adam.ganky.base.IPresenter
import com.adam.ganky.base.IView
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.base.IModel
import io.reactivex.Observable

/**
 * Created by yu on 2017/6/20.
 */
interface IDetail {
    interface View : IView {
        fun showGirl(url: String)

        fun onFavoriteChange(isFavorite: Boolean)
    }

    interface Presenter : IPresenter {
        fun getGirl()

        fun isFavorite(id: String)

        fun addToFavorites(entity: GankEntity)

        fun removeById(id: String)
    }

    interface Repository : IModel {
        fun getGirl(): Observable<List<GankEntity>>

        fun queryById(id: String): GankEntity?

        fun addToFavorites(entity: GankEntity)

        fun removeById(id: String)
    }

}