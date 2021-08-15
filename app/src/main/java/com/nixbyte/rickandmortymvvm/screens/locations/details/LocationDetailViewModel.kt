package com.nixbyte.rickandmortymvvm.screens.locations.details

import androidx.lifecycle.SavedStateHandle
import com.nixbyte.platform.viewmodel.BaseViewModel

class LocationDetailViewModel(screen: LocationDetailFragment.Screen
                             ,savedStateHandle: SavedStateHandle) : BaseViewModel() {
    private val _location = savedStateHandle.getLiveData("", screen.location)
    val location = _location
}