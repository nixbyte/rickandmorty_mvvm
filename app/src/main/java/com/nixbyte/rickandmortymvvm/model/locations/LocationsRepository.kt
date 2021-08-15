package com.nixbyte.rickandmortymvvm.model.locations

import com.nixbyte.platform.model.Repository
import com.nixbyte.platform.model.Task
import com.nixbyte.rickandmortymvvm.model.api.domain.Location

interface LocationsRepository : Repository {
    fun getAllLocations() : Task<List<Location>?>
    fun getLocationById(id: String) : Task<Location?>
    fun getMultipleLocations(ids: String) : Task<List<Location>?>
}