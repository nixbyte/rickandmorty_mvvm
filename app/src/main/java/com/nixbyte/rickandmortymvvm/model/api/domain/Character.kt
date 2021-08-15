package com.nixbyte.rickandmortymvvm.model.api.domain

import android.os.Parcelable
import android.view.View
import androidx.room.Ignore
import com.nixbyte.rickandmortymvvm.common.recyclerview.ItemClickable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) : Parcelable, ItemClickable {
    @Ignore
    @IgnoredOnParcel
    @Volatile
    override var onItemClick: (View) -> Unit = {}
}