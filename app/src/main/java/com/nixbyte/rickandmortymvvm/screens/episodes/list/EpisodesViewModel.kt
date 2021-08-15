package com.nixbyte.rickandmortymvvm.screens.episodes.list

import com.nixbyte.platform.navigation.Navigation
import com.nixbyte.platform.uiactions.UiActions
import com.nixbyte.platform.viewmodel.ListViewModel
import com.nixbyte.rickandmortymvvm.R
import com.nixbyte.rickandmortymvvm.common.recyclerview.PaginatedRecyclerView
import com.nixbyte.rickandmortymvvm.model.api.domain.Episode
import com.nixbyte.rickandmortymvvm.model.episodes.EpisodesRepository
import com.nixbyte.rickandmortymvvm.screens.episodes.detail.EpisodeDetailFragment

class EpisodesViewModel(private val navigation: Navigation
                        ,private val uiActions: UiActions
                        ,private val episodesRepository: EpisodesRepository
)
    : ListViewModel<Episode>(R.layout.episode_list_item, { view, episode, position ->
    navigation.addScreen(EpisodeDetailFragment.Screen(episode))
})  {

    init {
        load()
    }

    fun getTitle() : String { return uiActions.getString(R.string.episode) }

    override fun load(offsetAndSize: PaginatedRecyclerView.OffsetAndSize) {
        val ids = preparePaginationRequestParameter(offsetAndSize)
        episodesRepository.getMultipleEpisodes(ids).into(_currentList)
        currentPaginationOffset.value = offsetAndSize
    }
}