package com.nixbyte.rickandmortymvvm.common.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseListAdapter<T: ItemClickable?>(
    val items: List<T> = mutableListOf(),
    private val itemLayoutId: Int,
    private val onItemClickAction: (View, T, Int) -> Unit = { _, _, _ -> }
): RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, itemLayoutId, parent, false)
        return createViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = items[holder.adapterPosition]
        item?.onItemClick = { view ->  onItemClickAction(view, item, position)}
        holder.bind(item)
    }

    open fun createViewHolder(binding: ViewDataBinding): BaseViewHolder<T> {
        return BaseViewHolder(binding)
    }

    open fun clear() {
        if(items is ArrayList) {
            items.clear()
            notifyItemRangeRemoved(0, items.size)
            notifyDataSetChanged()
        }
    }

    open fun add(list: List<T>) {
        if(items is ArrayList) {
            items.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun isEmpty(): Boolean = items.isEmpty()
}