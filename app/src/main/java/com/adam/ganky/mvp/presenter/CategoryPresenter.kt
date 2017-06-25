package com.adam.ganky.mvp.presenter

import com.adam.ganky.base.BasePresenter
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.mvp.repository.CategoryRepository
import com.adam.ganky.rx.ApiSubscriber
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
        load(type, pageSize, false)
    }

    override fun loadMore(type: String, pageSize: Int) {
        pageNum++
        load(type, pageSize, true)
    }

    private fun load(type: String, pageSize: Int, isLoadmore: Boolean) {
        repository.loadData(type, pageSize, pageNum)
                .compose(RxUtils.apiTransformer(mView))
                .subscribe(object : ApiSubscriber<List<GankEntity>>(mView) {
                    override fun onNext(data: List<GankEntity>) {
                        if (isLoadmore) {
                            mView?.onLoadMore(data, data.size == pageSize)
                        } else {
                            mView?.onRefresh(data)
                        }
                    }

                    override fun onFail(e: Throwable?) {
                        mView?.onError()
                    }
                })
    }
}