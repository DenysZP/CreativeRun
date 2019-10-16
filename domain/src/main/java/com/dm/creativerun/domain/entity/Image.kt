package com.dm.creativerun.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(val src: String, val width: Int, val height: Int): Parcelable