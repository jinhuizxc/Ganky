package com.adam.ganky.ui.adapter

import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.View
import com.adam.ganky.R
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ScaleType
import com.adam.ganky.http.displayImage
import com.adam.ganky.util.CategoryType
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author yu
 * Create on 2017/6/22.
 */
class CategoryAdapter(val fragment: Fragment, data: List<GankEntity>?,
                      listener: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit)
    : BaseMultiItemQuickAdapter<GankEntity, BaseViewHolder>(data) {

    init {
        addItemType(CategoryType.ANDROID.type, R.layout.item_android)// android iOS web采用相同的type
        addItemType(CategoryType.GIRLS.type, R.layout.item_girls)
        setOnItemClickListener { adapter, view, position -> listener(adapter, view, position) }
    }

    override fun convert(helper: BaseViewHolder, item: GankEntity) {
        when (helper.itemViewType) {
            // android iOS web三个的type是一样的
            CategoryType.ANDROID.type -> {
                helper.setText(R.id.tvDesc, item.desc)
                        .setText(R.id.tvAuthor, if (TextUtils.isEmpty(item.who)) "UnKnow" else item.who)
                        .setText(R.id.tvDate, item.publishedAt)
                        .setImageResource(R.id.ivImage,
                                if (item.type == CategoryType.ANDROID.nameStr) R.mipmap.icon_android
                                else R.mipmap.icon_apple)
            }
            else -> {
                // 演示简单封装的DSL风格图片显示，内部其实还是Glide
                displayImage {
                    this.fragment = this@CategoryAdapter.fragment
                    url = item.url
                    imageView = helper.getView(R.id.ivImage)
                    scaleType = ScaleType.CENTER_CROP
                }
            }
        }
    }
}