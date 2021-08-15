package com.nixbyte.rickandmortymvvm.screens.locations.details

import androidx.lifecycle.SavedStateHandle
import com.nixbyte.platform.viewmodel.AbstractViewModel

class LocationDetailViewModel(screen: LocationDetailFragment.Screen
                             ,savedStateHandle: SavedStateHandle) : AbstractViewModel() {
    private val _location = savedStateHandle.getLiveData("", screen.location)
    val location = _location
}