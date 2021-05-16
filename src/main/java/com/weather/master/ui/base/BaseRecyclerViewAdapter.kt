package com.weather.master.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseRecyclerViewAdapter<ITEM_TYPE, VIEW_HOLDER : BaseViewHolder<ITEM_TYPE>> :
    RecyclerView.Adapter<VIEW_HOLDER>() {

    protected lateinit var context: Context
    protected var dataList: MutableList<ITEM_TYPE> = ArrayList<ITEM_TYPE>()

    abstract fun getRowLayoutId(viewType: Int): Int

    abstract fun bind(viewHolder: VIEW_HOLDER, position: Int, item: ITEM_TYPE)

    abstract fun getViewHolder(view: View, viewType: Int): VIEW_HOLDER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIEW_HOLDER {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(getRowLayoutId(viewType), parent, false)
        return getViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int) {
        bind(holder, position, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    open fun add(item: ITEM_TYPE) {
        dataList.add(item)
        notifyDataSetChanged()
    }

    open fun add(position: Int, item: ITEM_TYPE) {
        dataList.add(position, item)
        notifyItemInserted(position)
    }

    open fun remove(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun addItems(items: MutableList<ITEM_TYPE>) {
        if (!items.isEmpty()) {
            val previousSize = itemCount
            if (dataList.addAll(items)) {
                notifyItemRangeInserted(previousSize, items.size)
            }
        }
    }

    fun setItems(items: MutableList<ITEM_TYPE>) {
        dataList = items
        notifyDataSetChanged()
    }

    fun clearItems() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun getItems(): MutableList<ITEM_TYPE> {
        return dataList
    }
}
