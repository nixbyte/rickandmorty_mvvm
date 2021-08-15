package com.nixbyte.rickandmortymvvm.model.api.domain

import android.os.Parcelable
import android.view.View
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import com.nixbyte.rickandmortymvvm.common.recyclerview.ItemClickable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    val id: Long,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) : Parcelable, ItemClickable {
    @Ignore
    @IgnoredOnParcel
    override var onItemClick: (View) -> Unit = {}
}