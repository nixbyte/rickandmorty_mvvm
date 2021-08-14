package com.nixbyte.rickandmortymvvm.model

import com.nixbyte.platform.model.Repository
import com.nixbyte.platform.model.Task
import com.nixbyte.rickandmortymvvm.model.api.domain.Location

typealias LocationsListListener = (List<Location>) -> Unit

interface LocationsRepository : Repository {
    fun getAllLocations() : Task<List<Location>>
    fun getLocationById(id: String) : Task<Location>
    fun getMultipleLocations(ids: String) : Task<List<Location>>

    fun addListener(listener: LocationsListListener)
    fun removeListener(listener: LocationsListListener)
}