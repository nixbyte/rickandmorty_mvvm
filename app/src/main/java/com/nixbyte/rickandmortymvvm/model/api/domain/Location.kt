package com.nixbyte.rickandmortymvvm.model.api.domain

import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.room.Ignore
import com.nixbyte.rickandmortymvvm.common.recyclerview.ItemClickable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val id: Long? = 0,
    val name: String?= "name",
    val type: String?= "type",
    val dimension: String?= "dimenstion",
    val residents: List<String?>? = listOf(""),
    val url: String? = "url",
    val created: String? = "created"
) : Parcelable, ItemClickable {

    @Ignore
    @IgnoredOnParcel
    override var onItemClick: (View) -> Unit = {}
}