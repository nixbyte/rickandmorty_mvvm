package com.nixbyte.rickandmortymvvm.model.api.model

import com.nixbyte.rickandmortymvvm.model.api.domain.All
import com.nixbyte.rickandmortymvvm.model.api.domain.Character
import com.nixbyte.rickandmortymvvm.model.api.domain.Episode
import com.nixbyte.rickandmortymvvm.model.api.domain.Location
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApiService {
    @GET("location")
    fun getAllLocations() : Single<Response<All<Location>>>

    @GET("location/{id}")
    fun getLocationById(@Path("id") id: String) : Single<Response<Location>>

    @GET("location/{id}")
    fun getMultipleLocations(@Path("id") ids: String) : Single<Response<List<Location>>>
}

interface CharacterApiService {
    @GET("character")
    fun getAllCharacters() : Single<Response<All<Character>>>

    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int) : Single<Response<Character>>

    @GET("character/{id}")
    fun getMultipleCharacters(@Path("id") ids: String) : Single<Response<List<Character>>>
}

interface EpisodeApiService {
    @GET("episode")
    fun getAllCharacters() : Single<Response<All<Episode>>>

    @GET("episode/{id}")
    fun getCharacterById( @Path("id") id: String) : Single<Response<Episode>>

    @GET("episode/{id}")
    fun getMultipleCharacters(@Path("ids") ids: String) : Single<Response<List<Episode>>>
}