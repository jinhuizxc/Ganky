package com.adam.ganky.mvp.presenter

import com.adam.ganky.base.BasePresenter
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ApiSubscriber
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.mvp.repository.CategoryRepository
import com.adam.ganky.rx.RxUtils
import javax.inject.Inject

/**
 * Created by yu on 2017/6/20.
 */
class CategoryPresenter
@Inject constructor(view: ICategory.View, val repository: CategoryRepository)
    : BasePresenter<ICategory.View>(view), ICategory.Presenter {

    var pageNum = 1

    override fun create() {

    }

    override fun refresh(type: String, pageSize: Int) {
        pageNum = 1
        repository.loadData(type, pageSize, pageNum)
                .compose(RxUtils.apiTransformer(mView))
                .subscribe(object : ApiSubscriber<List<GankEntity>>(mView) {
                    override fun onNext(t: List<GankEntity>) {
                        mView?.onRefresh(t)
                    }
                })
    }

    override fun loadMore(type: String, pageSize: Int) {
        repository.loadData(type, pageSize, pageNum)
                .compose(RxUtils.apiTransformer(mView))
                .subscribe(object : ApiSubscriber<List<GankEntity>>(mView) {
                    override fun onNext(data: List<GankEntity>?) {
                        pageNum++
                        if (data == null || data.isEmpty())
                            mView?.onNoMore()
                        else
                            mView?.onLoadMore(data)
                    }
                })
    }
}