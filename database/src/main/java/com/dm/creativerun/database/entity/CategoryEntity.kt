package com.dm.creativerun.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity internal constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
    val isPopular: Boolean
)