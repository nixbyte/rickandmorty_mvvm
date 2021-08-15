package com.nixbyte.rickandmortymvvm.model.episodes

import com.nixbyte.platform.model.Repository
import com.nixbyte.platform.model.Task
import com.nixbyte.rickandmortymvvm.model.api.domain.Episode

interface EpisodesRepository : Repository {
    fun getAllEpisodes() : Task<List<Episode>?>
    fun getEpisodeById(id: String) : Task<Episode?>
    fun getMultipleEpisodes(ids: String) : Task<List<Episode>?>
}