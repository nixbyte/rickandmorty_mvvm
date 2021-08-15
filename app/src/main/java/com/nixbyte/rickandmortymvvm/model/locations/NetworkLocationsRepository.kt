package com.nixbyte.rickandmortymvvm.model.locations

import com.nixbyte.platform.model.RxTasksFactory
import com.nixbyte.platform.model.Task
import com.nixbyte.rickandmortymvvm.model.api.API
import com.nixbyte.rickandmortymvvm.model.api.domain.Location

class NetworkLocationsRepository(private val taskFactory: RxTasksFactory
) : LocationsRepository {

    override fun getAllLocations(): Task<List<Location>?> = taskFactory.async {
       return@async API.LocationApiService().getAllLocations().map {
           return@map it.body()?.results
       }
    }

    override fun getLocation(url: String): Task<Location?> = taskFactory.async {
        return@async API.LocationApiService().getLocation(url).map {
            return@map it.body()
        }
    }

    override fun getMultipleLocations(ids: String): Task<List<Location>?> = taskFactory.async {
        return@async API.LocationApiService().getMultipleLocations(ids).map {
            return@map it.body()
        }
    }
}