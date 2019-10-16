package com.dm.creativerun.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stats(val views: Int, val appreciations: Int, val comments: Int): Parcelable