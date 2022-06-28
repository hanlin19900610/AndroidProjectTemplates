package com.mufeng.baselibrary.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mufeng.baselibrary.R


fun BaseQuickAdapter<*, *>.setEmptyData(
    imgRes: Int = R.mipmap.img_zwhy,
    tip: String = "暂无数据",
    reRefreshData: () -> Unit = {}
) {

    val emptyView = LayoutInflater.from(context).inflate(R.layout.state_empty_layout, null)
    val imageView = emptyView.findViewById<ImageView>(R.id.ivNoData)
    imageView.load(imgRes)
    val textView = emptyView.findViewById<TextView>(R.id.emptyStatusTextView)
    textView.text = tip

    imageView.clickWithTrigger {
        reRefreshData.invoke()
    }

    setEmptyView(emptyView)
    isUseEmpty = false

}

// adapter点击事件， 500毫秒内只响应1次
fun BaseQuickAdapter<*, *>.setOnItemClick(
    duration: Long = 500,
    action: (view: View, position: Int) -> Unit
) {
    val _clickCache_ = hashMapOf<Int, Runnable>()
    this.setOnItemClickListener { _, view, position ->
        if (!_clickCache_.containsKey(view.id)) {
            //unclicked
            _clickCache_[view.id] = Runnable { _clickCache_.remove(view.id) }
            action(view, position)
        }
        view.removeCallbacks(_clickCache_[view.id])
        view.postDelayed(_clickCache_[view.id], duration)
    }
}

fun BaseQuickAdapter<*, *>.setOnItemChildClick(
    duration: Long = 500,
    vararg ids: Int,
    action: (view: View, position: Int) -> Unit
) {
    val _clickCache_ = hashMapOf<Int, Runnable>()
    this.addChildClickViewIds(*ids)
    this.setOnItemChildClickListener { _, view, position ->
        if (!_clickCache_.containsKey(view.id)) {
            //unclicked
            _clickCache_[view.id] = Runnable { _clickCache_.remove(view.id) }
            action(view, position)
        }
        view.removeCallbacks(_clickCache_[view.id])
        view.postDelayed(_clickCache_[view.id], duration)
    }
}

@JvmName("bind")
fun <VB : ViewBinding> BaseViewHolder.withBinding(bind: (View) -> VB): BaseViewHolder =
    BaseViewHolderWithBinding(bind(itemView))

@JvmName("getBinding")
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> BaseViewHolder.getViewBinding(): VB {
    if (this is BaseViewHolderWithBinding<*>) {
        return binding as VB
    } else {
        throw IllegalStateException("The binding could not be found.")
    }
}

class BaseViewHolderWithBinding<VB : ViewBinding>(val binding: VB) : BaseViewHolder(binding.root)