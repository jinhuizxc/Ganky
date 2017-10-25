package com.adam.gankarch.common.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.adam.gankarch.R
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity


/**
 * 基于BRAH扩展的DataBindingAdapter
 * Created by yu on 2017/10/23.
 */
abstract class DataBindingAdapter<T : MultiItemEntity>(data: List<T>?)
    : BaseMultiItemQuickAdapter<T, DatabindingViewHolder>(data) {

    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)

        return binding.root.apply { setTag(R.id.BaseQuickAdapter_databinding_support, binding) }
    }

    override final fun convert(helper: DatabindingViewHolder, item: T) {
        convert(helper, item, helper.getBinding())
    }

    abstract fun convert(helper: DatabindingViewHolder, item: T, binding: ViewDataBinding)
}

class DatabindingViewHolder(view: View) : BaseViewHolder(view) {

    fun getBinding(): ViewDataBinding {
        return itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
    }
}