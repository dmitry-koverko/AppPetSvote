package com.petsvote.rating

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(var mItemList: List<String?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false)
            ItemViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(@NonNull viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            populateItemRows(viewHolder as ItemViewHolder, position)
        } else if (viewHolder is LoadingViewHolder) {
            showLoadingView(viewHolder as LoadingViewHolder, position)
        }
    }

    fun itemCount(): Int =if (mItemList == null) 0 else mItemList!!.size

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return if (mItemList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private inner class ItemViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvItem: TextView

        init {
            tvItem = itemView.findViewById(R.id.tvItem)
        }
    }

    private inner class LoadingViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed
    }

    private fun populateItemRows(viewHolder: ItemViewHolder, position: Int) {
        val item = mItemList!![position]
        viewHolder.tvItem.setText(item)
    }

    override fun getItemCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

}