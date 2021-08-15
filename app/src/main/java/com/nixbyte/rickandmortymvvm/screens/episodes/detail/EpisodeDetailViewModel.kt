package com.nixbyte.rickandmortymvvm.screens.episodes.detail

import androidx.lifecycle.SavedStateHandle
import com.nixbyte.platform.viewmodel.BaseViewModel

class EpisodeDetailViewModel(screen: EpisodeDetailFragment.Screen
                             ,savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _episode = savedStateHandle.getLiveData("", screen.episode)
    val episode = _episode
}