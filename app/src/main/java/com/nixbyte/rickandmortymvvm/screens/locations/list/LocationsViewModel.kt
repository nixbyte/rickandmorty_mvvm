package com.nixbyte.rickandmortymvvm.screens.locations.list

import android.util.Log
import com.nixbyte.platform.model.PendingResult
import com.nixbyte.platform.model.SuccessResult
import com.nixbyte.platform.navigation.Navigation
import com.nixbyte.platform.uiactions.UiActions
import com.nixbyte.platform.viewmodel.AbstractViewModel
import com.nixbyte.platform.viewmodel.LiveResult
import com.nixbyte.platform.viewmodel.MutableLiveResult
import com.nixbyte.platform.viewmodel.preparePaginationRequestParameter
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.common.PaginatedRecyclerView
import com.nixbyte.rickandmortymvvm.model.api.domain.Location
import com.nixbyte.rickandmortymvvm.model.LocationsListListener
import com.nixbyte.rickandmortymvvm.model.LocationsRepository

class LocationsViewModel(private val navigation: Navigation
                        ,private val uiActions: UiActions
                        ,private val locationsRepository: LocationsRepository
                        ,) : AbstractViewModel()  {

    private val _currentLocations = MutableLiveResult<List<Location>>(PendingResult())
    val currentLocations: LiveResult<List<Location>> = _currentLocations

    private val locationsListener: LocationsListListener = {
        _currentLocations.postValue(SuccessResult(it))
    }

    init {
        locationsRepository.addListener(locationsListener)
        load()
    }

    override fun onCleared() {
        super.onCleared()
        locationsRepository.removeListener(locationsListener)
    }

    override fun onViewModelResult(result: Any) {
        super.onViewModelResult(result)
        val message = uiActions.getString(R.string.locations, result)
        uiActions.toast(message)
    }

    fun tryAgain() {
        load()
    }

    fun loadMoreItems(offsetAndSize: PaginatedRecyclerView.OffsetAndSize) {
        load(offsetAndSize)
    }

    private fun load(offsetAndSize: PaginatedRecyclerView.OffsetAndSize = PaginatedRecyclerView.OffsetAndSize(0,10)) {
        val ids = preparePaginationRequestParameter(offsetAndSize)
        locationsRepository.getMultipleLocations(ids).into(_currentLocations)
    }
}

