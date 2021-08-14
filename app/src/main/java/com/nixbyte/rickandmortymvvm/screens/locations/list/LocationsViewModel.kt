package com.nixbyte.rickandmortymvvm.screens.locations.list

import android.util.Log
import com.nixbyte.platform.model.PendingResult
import com.nixbyte.platform.model.SuccessResult
import com.nixbyte.platform.navigation.Navigation
import com.nixbyte.platform.uiactions.UiActions
import com.nixbyte.platform.viewmodel.AbstractViewModel
import com.nixbyte.platform.viewmodel.LiveResult
import com.nixbyte.platform.viewmodel.MutableLiveResult
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

    fun load(offsetAndSize: PaginatedRecyclerView.OffsetAndSize = PaginatedRecyclerView.OffsetAndSize(0,10)) {
        var ids = ""
        for (i in offsetAndSize.offset .. offsetAndSize.offset+offsetAndSize.pageSize) {
            ids += if (i == 0) "${i+1}" else  ",${i+1}"
        }

        Log.e("Load", "offset ${offsetAndSize.offset} size ${offsetAndSize.pageSize}")
        locationsRepository.getMultipleLocations(ids).into(_currentLocations)
    }
}