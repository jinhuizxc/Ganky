package com.adam.ganky.mvp

import com.adam.ganky.base.BasePresenter
import com.adam.ganky.rx.RxUtils
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 * Created by yu on 2017/6/20.
 */
class CategoryPresenter : BasePresenter<ICategory.View>(), ICategory.Presenter {

    @Inject lateinit var repository: CategoryRepository

    var pageNum = 1

    override fun create() {

    }

    override fun refresh(type: String, pageSize: Int) {
        pageNum = 1
        repository.loadData(type, pageSize, pageNum)
                .compose(RxUtils.apiTransformer(mView))
                .subscribe(Consumer {

                })
    }

    override fun loadMore(type: String, pageSize: Int) {
        pageNum++
        repository.loadData(type, pageSize, pageNum)
                .compose(RxUtils.apiTransformer(mView))
                .subscribe(Consumer {

                })
    }
}