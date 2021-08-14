package com.nixbyte.rickandmortymvvm.model.api.domain

import com.google.gson.annotations.SerializedName

data class Episode(
    val id: Long,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)