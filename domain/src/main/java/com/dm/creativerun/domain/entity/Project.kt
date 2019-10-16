package com.dm.creativerun.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Project(
    val id: Long,
    val name: String,
    val date: Date,
    val url: String,
    val categories: List<String>,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val stats: Stats,
    val colors: List<Int>
): Parcelable