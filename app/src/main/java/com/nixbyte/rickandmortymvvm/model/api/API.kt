package com.nixbyte.rickandmortymvvm.model.api

import com.nixbyte.rickandmortymvvm.model.api.model.CharacterApiService
import com.nixbyte.rickandmortymvvm.model.api.model.EpisodeApiService
import com.nixbyte.rickandmortymvvm.model.api.model.LocationApiService
import okhttp3.internal.http2.Header
import okhttp3.logging.HttpLoggingInterceptor
import com.nixbyte.platform.model.ApiConstructor

/**
 * Created by nixbyte on 20,Декабрь,2019
 */

object API {
    private const val TEST_API = "https://rickandmortyapi.com"
    private const val PRODUCTION_API = "https://rickandmortyapi.com"
    private const val API_VERSION = "/api/"

    const val DEFAULT_HOST = TEST_API + API_VERSION

    private var A = ApiConstructor.Builder()
        .setHeader(Header("Content-Type","application/json")) //set header for single value headers like Content-Type, Authorization etc
//        .addHeader(Header("Authorization","Bearer asdfadf")) //add header method for multiple value headers list Cache-Control
        .setLogLevel(HttpLoggingInterceptor.Level.BODY)

    fun LocationApiService(): LocationApiService {
        return A.create(LocationApiService::class.java)
    }

    fun CharacterApiService(): CharacterApiService {
        return A.create( CharacterApiService::class.java)
    }

    fun EpisodeApiService(): EpisodeApiService {
        return A.create(EpisodeApiService::class.java)
    }
}
