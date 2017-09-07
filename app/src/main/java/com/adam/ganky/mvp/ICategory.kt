package com.adam.ganky.mvp

import com.adam.ganky.base.IModel
import com.adam.ganky.base.IPresenter
import com.adam.ganky.base.IView
import com.adam.ganky.entity.GankEntity
import io.reactivex.Observable

/**
 * Created by yu on 2017/6/20.
 */
interface ICategory {
    interface View : IView {
        fun onRefresh(data: List<GankEntity>)
        fun onLoadMore(data: List<GankEntity>, hasMore: Boolean)
    }

    interface Presenter : IPresenter {
        fun refresh(type: String, pageSize: Int = 10)
        fun loadMore(type: String, pageSize: Int = 10)
    }

    interface Repository : IModel {
        fun loadData(type: String, pageSize: Int = 10, pageNum: Int = 1): Observable<List<GankEntity>>
    }

}