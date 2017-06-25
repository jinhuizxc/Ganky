package com.adam.ganky.mvp

import com.adam.ganky.base.IPresenter
import com.adam.ganky.base.IView
import com.adam.ganky.entity.GankEntity
import com.yu.gank4k2.base.IModel
import io.reactivex.Observable

/**
 * @author yu
 * Create on 2017/6/22.
 */
interface ICollection {

    interface View : IView {
        fun onRefresh(data: List<GankEntity>)
        fun onLoadMore(data: List<GankEntity>, hasMore: Boolean)
        fun onRemove()
    }

    interface Presenter : IPresenter {
        fun refresh(pageSize: Int = 10)
        fun loadMore(pageSize: Int = 10)
        fun removeById(id: String)
    }

    interface Repository : IModel {
        fun getCollections(page: Int, pageSize: Int): Observable<List<GankEntity>>
        fun removeById(id: String)
    }
}