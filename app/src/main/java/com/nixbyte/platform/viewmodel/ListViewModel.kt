package com.nixbyte.platform.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nixbyte.platform.model.PendingResult
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginatedRecyclerView
import com.nixbyte.rickandmortymvvm.common.recyclerview.ItemAction
import com.nixbyte.rickandmortymvvm.common.recyclerview.ItemClickable
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginationListAdapter

abstract class ListViewModel<T : ItemClickable>(layoutId: Int, onItemClickAction: ItemAction<T>) : BaseViewModel() {

    val TAG = ListViewModel::class.simpleName
    protected val _currentList = MutableLiveResult<List<T>?>(PendingResult())
    val currentList: LiveResult<List<T>?> = _currentList
    val adapter = PaginationListAdapter<T>(
        layoutId
        ,onItemClickAction)

    protected val currentPaginationOffset = MutableLiveData<PaginatedRecyclerView.OffsetAndSize>()

    protected fun preparePaginationRequestParameter(offsetAndSize: PaginatedRecyclerView.OffsetAndSize) : String {
        var ids = ""
        for (i in offsetAndSize.offset until offsetAndSize.offset+offsetAndSize.pageSize) {
            ids += ",${i+1}"
        }
        return ids.trimStart {
            it == ','
        }
    }

    protected abstract fun load(offsetAndSize: PaginatedRecyclerView.OffsetAndSize = PaginatedRecyclerView.OffsetAndSize(0,10))

    fun loadNext(offsetAndSize: PaginatedRecyclerView.OffsetAndSize) {
        currentPaginationOffset.value?.run {
            if(offsetAndSize.offset > offset)
                load(offsetAndSize)
        }
    }

    fun refresh() {
        adapter.refresh()
        currentPaginationOffset.value = PaginatedRecyclerView.OffsetAndSize(0,10)
        load()
    }

    fun addToAdapter(items: List<T>?) {
        currentPaginationOffset.value?.run {
            items?.let {
                if(!adapter.getList().containsAll(items))
                    adapter.addMore(items, pageSize)
            }
        }
    }
}