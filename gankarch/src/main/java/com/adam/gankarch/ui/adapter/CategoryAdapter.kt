package com.adam.gankarch.ui.adapter

import android.view.View
import com.adam.gankarch.R
import com.adam.gankarch.common.base.DataBindingAdapter
import com.adam.gankarch.common.base.DatabindingViewHolder
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.databinding.ItemAndroidBinding
import com.adam.gankarch.databinding.ItemGirlsBinding
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Created by yu on 2017/10/26.
 */
class CategoryAdapter(data: List<GankEntity>?, listener: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit)
    : DataBindingAdapter<GankEntity>(data) {

    init {
        addItemType(0, R.layout.item_android)
        addItemType(1, R.layout.item_girls)
        setOnItemClickListener { adapter, view, position -> listener(adapter, view, position) }
    }

    override fun convert(helper: DatabindingViewHolder, item: GankEntity) {
        val binding = helper.getBinding()
        if (binding != null) {
            when (helper.itemViewType) {
                0 -> (binding as ItemAndroidBinding).entity = item
                1 -> (binding as ItemGirlsBinding).entity = item
            }
            helper.setImageResource(R.id.ivImage, if (item.type == "Android") R.mipmap.icon_android else R.mipmap.icon_apple)
        }
    }
}