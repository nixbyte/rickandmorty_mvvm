package com.nixbyte.rickandmortymvvm.common.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.nixbyte.rickandmortymvvm.R

typealias ItemAction<T> = (View, T?, Int) -> Unit

class PaginationListAdapter<T : ItemClickable?>(
    itemLayoutId: Int,
    onItemClickAction: ItemAction<T> = { _, _, _ -> },
    items: List<T?> = arrayListOf()
) : BaseListAdapter<T?>(items, itemLayoutId, onItemClickAction),
    PaginationListenable {

    val TAG = PaginationListAdapter::class.simpleName

    var isPaginationLocked = false
    private var isLoading = false
    private var isListEnded = false

    enum class HolderType {
        DATA, LOADING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T?> {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == HolderType.LOADING.ordinal) {
            val binding: ViewDataBinding =
                DataBindingUtil.inflate(inflater, R.layout.progress_list_item, parent, false)
            BaseViewHolder(binding)
        } else {
            super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position >= items.size - 1)
            HolderType.LOADING.ordinal
        else
            HolderType.DATA.ordinal
    }

    override fun setLoading() {
        if (!isLoading) {
            isLoading = true
            if (items is ArrayList) {
                items.add(null)
                notifyItemInserted(itemCount - 1)
            }
        }
    }

    override fun removeLoading() {
        if (isLoading) {
            isLoading = false
            if (items is ArrayList) {
                items.remove(items.last())
                notifyItemRemoved(itemCount)
            }
        }
    }

    override fun isPaginationEnabled(): Boolean {
        return !isListEnded && !isLoading && !isPaginationLocked
    }

    fun refresh() {
        removeLoading()
        isListEnded = false
        isPaginationLocked = false
        clear()
    }

    fun addMore(list: List<T>, requieredPageSize: Int) {
        if (list.size < requieredPageSize)
            isListEnded = true
        add(list)
    }
}