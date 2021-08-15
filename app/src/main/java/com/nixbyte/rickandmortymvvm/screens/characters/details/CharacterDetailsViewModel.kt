package com.nixbyte.rickandmortymvvm.screens.characters.details

import androidx.lifecycle.SavedStateHandle
import com.nixbyte.platform.model.PendingResult
import com.nixbyte.platform.viewmodel.BaseViewModel
import com.nixbyte.platform.viewmodel.MutableLiveResult
import com.nixbyte.rickandmortymvvm.model.api.domain.Location
import com.nixbyte.rickandmortymvvm.model.locations.LocationsRepository

class CharacterDetailsViewModel(screen: CharacterDetailsFragment.Screen
                                ,savedStateHandle: SavedStateHandle
                                ,private val locationsRepository: LocationsRepository
) : BaseViewModel() {
    private val _character = savedStateHandle.getLiveData(screen::class.simpleName.toString(),screen.character)
    private val _location = MutableLiveResult<Location?>(PendingResult())
    val character = _character
    val location = _location

    init {
        load()
    }

    private fun load() {
        character.value?.location?.url?.let {  url ->
            locationsRepository.getLocation(url).into(_location)
        }
    }
}
