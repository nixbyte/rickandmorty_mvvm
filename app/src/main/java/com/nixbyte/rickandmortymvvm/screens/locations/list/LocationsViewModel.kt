package com.nixbyte.rickandmortymvvm.screens.locations.list

import com.nixbyte.platform.navigation.Navigation
import com.nixbyte.platform.uiactions.UiActions
import com.nixbyte.platform.viewmodel.ListViewModel
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginatedRecyclerView
import com.nixbyte.rickandmortymvvm.model.api.domain.Location
import com.nixbyte.rickandmortymvvm.model.locations.LocationsRepository
import com.nixbyte.rickandmortymvvm.screens.locations.details.LocationDetailFragment

class LocationsViewModel(private val navigation: Navigation
                        ,private val uiActions: UiActions
                        ,private val locationsRepository: LocationsRepository)
    : ListViewModel<Location>(R.layout.locations_list_item, { view, location, position ->
        navigation.addScreen(LocationDetailFragment.Screen(location))
    })  {

    init {
        load()
    }

    fun getTitle() : String { return uiActions.getString(R.string.locations) }

    override fun load(offsetAndSize: PaginatedRecyclerView.OffsetAndSize) {
        val ids = preparePaginationRequestParameter(offsetAndSize)
        locationsRepository.getMultipleLocations(ids).into(_currentList)
        currentPaginationOffset.value = offsetAndSize
    }
}

