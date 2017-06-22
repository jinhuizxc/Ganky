package com.adam.ganky.mvp

import com.adam.ganky.base.IPresenter
import com.adam.ganky.base.IView
import com.adam.ganky.entity.GankEntity
import com.yu.gank4k2.base.IModel
import io.reactivex.Observable

/**
 * Created by yu on 2017/6/20.
 */
interface IDetail {
    interface View : IView {
        fun showGirl(url: String)
    }

    interface Presenter : IPresenter {
        fun getGirl()
    }

    interface Repository : IModel {
        fun getGirl(): Observable<List<GankEntity>>
    }

}