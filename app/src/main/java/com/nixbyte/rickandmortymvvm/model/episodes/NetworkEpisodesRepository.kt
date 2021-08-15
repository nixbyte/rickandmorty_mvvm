package com.nixbyte.rickandmortymvvm.model.episodes

import com.nixbyte.platform.model.RxTasksFactory
import com.nixbyte.platform.model.Task
import com.nixbyte.rickandmortymvvm.model.api.API
import com.nixbyte.rickandmortymvvm.model.api.domain.Episode

class NetworkEpisodesRepository(private val taskFactory: RxTasksFactory) : EpisodesRepository {

    override fun getAllEpisodes(): Task<List<Episode>?> = taskFactory.async {
        return@async API.EpisodeApiService().getAllEpisodes().map {
            return@map it.body()?.results
        }
    }

    override fun getEpisodeById(id: String): Task<Episode?> = taskFactory.async {
        return@async API.EpisodeApiService().getEpisodeById(id).map {
            return@map it.body()
        }
    }

    override fun getMultipleEpisodes(ids: String): Task<List<Episode>?> = taskFactory.async {
        return@async  API.EpisodeApiService().getMultipleEpisodes(ids).map {
            return@map it.body()
        }
    }
}