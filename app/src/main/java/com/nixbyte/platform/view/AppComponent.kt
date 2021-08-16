package com.nixbyte.platform.view

import com.nixbyte.platform.model.Repository
import com.nixbyte.platform.model.RxTaskFactory
import com.nixbyte.platform.model.RxTasksFactory
import com.nixbyte.rickandmortymvvm.model.characters.NetworkCharactersRepository
import com.nixbyte.rickandmortymvvm.model.episodes.NetworkEpisodesRepository
import com.nixbyte.rickandmortymvvm.model.locations.NetworkLocationsRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    val repositories: List<Repository>
}

@Module
object AppModule {

    @Singleton
    @Provides
    fun repositories(taskFactory: RxTasksFactory) : List<Repository> {
        return listOf(
            provideLocationRepository(taskFactory)
            ,provideCharacterRepository(taskFactory)
            ,provideEpisodeRepository(taskFactory)
        )
    }

    @Singleton
    @Provides
    fun provideLocationRepository(taskFactory: RxTasksFactory) : Repository {
        return NetworkLocationsRepository(taskFactory)
    }

    @Singleton
    @Provides
    fun provideCharacterRepository(taskFactory: RxTasksFactory) : Repository {
        return NetworkCharactersRepository(taskFactory)
    }

    @Singleton
    @Provides
    fun provideEpisodeRepository(taskFactory: RxTasksFactory) : Repository {
        return NetworkEpisodesRepository(taskFactory)
    }

    @Singleton
    @Provides
    fun provideTaskFactory() : RxTasksFactory {
        return RxTaskFactory()
    }
}