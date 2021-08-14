package com.nixbyte.rickandmortymvvm.model

import com.nixbyte.platform.model.RxTasksFactory
import com.nixbyte.platform.model.Task
import com.nixbyte.rickandmortymvvm.model.api.API
import com.nixbyte.rickandmortymvvm.model.api.domain.Location

class NetworkLocationsRepository(private val taskFactory: RxTasksFactory
) : LocationsRepository {

    private val listeners = mutableSetOf<LocationsListListener>()

    override fun addListener(listener: LocationsListListener) {
        listeners += listener
    }

    override fun removeListener(listener: LocationsListListener) {
        listeners -= listener
    }

    override fun getAllLocations(): Task<List<Location>> = taskFactory.async {
       return@async API.LocationApiService().getAllLocations().map {
           return@map it.body()!!.results
       }
    }

    override fun getLocationById(id: String): Task<Location> = taskFactory.async {
        return@async API.LocationApiService().getLocationById(id).map {
            return@map it.body()!!
        }
    }

    override fun getMultipleLocations(ids: String): Task<List<Location>> = taskFactory.async {
        return@async API.LocationApiService().getMultipleLocations(ids).map {
            return@map it.body()!!
        }
    }
}