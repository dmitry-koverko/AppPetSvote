package com.petsvote.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

class FingerprintListAdapter(
    private val fingerprints: List<ItemFingerprint<*, *>>,
) : ListAdapter<Item, BaseViewHolder<ViewBinding, Item>>(
    FingerprintDiffUtil(fingerprints)
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, Item> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseViewHolder<ViewBinding, Item> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, Item>, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, Item>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            getItem(position)?.let { holder.onBind(it, payloads) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return fingerprints.find { item?.let { it1 -> it.isRelativeItem(it1) } == true }
            ?.getLayoutId()
            ?: throw IllegalArgumentException("View type not found: $item")
    }

}