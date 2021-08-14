package com.nixbyte.rickandmortymvvm.screens.locations.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nixbyte.platform.model.PendingResult
import com.nixbyte.platform.model.SuccessResult
import com.nixbyte.platform.model.takeSuccess
import com.nixbyte.platform.navigation.Navigation
import com.nixbyte.platform.uiactions.UiActions
import com.nixbyte.platform.viewmodel.AbstractViewModel
import com.nixbyte.platform.viewmodel.LiveResult
import com.nixbyte.platform.viewmodel.MutableLiveResult
import com.nixbyte.platform.viewmodel.preparePaginationRequestParameter
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.common.PaginatedRecyclerView
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginationListAdapter
import com.nixbyte.rickandmortymvvm.model.api.domain.Location
import com.nixbyte.rickandmortymvvm.model.LocationsListListener
import com.nixbyte.rickandmortymvvm.model.LocationsRepository

class LocationsViewModel(private val navigation: Navigation
                        ,private val uiActions: UiActions
                        ,private val locationsRepository: LocationsRepository) : AbstractViewModel()  {

    private val _currentLocations = MutableLiveResult<List<Location>>(PendingResult())
    val currentLocations: LiveResult<List<Location>> = _currentLocations

    private var _currentPaginationOffset = MutableLiveData<PaginatedRecyclerView.OffsetAndSize>()

    private val locationsListener: LocationsListListener = {
        _currentLocations.postValue(SuccessResult(it))
    }

    val adapter = PaginationListAdapter<Location>(R.layout.locations_list_item)

    init {
        locationsRepository.addListener(locationsListener)
        load()
    }

    override fun onCleared() {
        super.onCleared()
        locationsRepository.removeListener(locationsListener)
    }

    fun tryAgain() {
        adapter.refresh()
        _currentPaginationOffset.value = PaginatedRecyclerView.OffsetAndSize(0,10)
        load()
    }

    fun loadMoreItems(offsetAndSize: PaginatedRecyclerView.OffsetAndSize) {
        _currentPaginationOffset.value?.run {
            if(offsetAndSize.offset > offset)
                load(offsetAndSize)
        }
    }

    fun addToAdapter(items: List<Location>) {
        _currentPaginationOffset.value?.run {
            if(!adapter.getList().containsAll(items))
                adapter.addMore(items, pageSize)
        }
    }

    private fun load(offsetAndSize: PaginatedRecyclerView.OffsetAndSize = PaginatedRecyclerView.OffsetAndSize(0,10)) {
        val ids = preparePaginationRequestParameter(offsetAndSize)
        locationsRepository.getMultipleLocations(ids).into(_currentLocations)
        _currentPaginationOffset.value = offsetAndSize
    }
}

