package com.nixbyte.rickandmortymvvm

import android.app.Application
import com.nixbyte.platform.model.Repository
import com.nixbyte.platform.model.RxTaskFactory
import com.nixbyte.platform.view.BaseApplication
import com.nixbyte.rickandmortymvvm.model.api.API
import com.nixbyte.rickandmortymvvm.model.characters.NetworkCharactersRepository
import com.nixbyte.rickandmortymvvm.model.locations.NetworkLocationsRepository
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class App : Application(), BaseApplication {

    private val rxTasksFactory = RxTaskFactory()

    override val repositories: List<Repository> = listOf(
        NetworkLocationsRepository(rxTasksFactory)
       ,NetworkCharactersRepository(rxTasksFactory)
    )

    override fun onCreate() {
        super.onCreate()
        val picassoBuilder = Picasso.Builder(applicationContext)
            .downloader(OkHttp3Downloader(API.A.okhttp()))
            .loggingEnabled(true)
        Picasso.setSingletonInstance(picassoBuilder.build())
    }
}