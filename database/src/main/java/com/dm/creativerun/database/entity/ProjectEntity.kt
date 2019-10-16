package com.dm.creativerun.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dm.creativerun.domain.entity.Stats
import java.util.*

@Entity
class ProjectEntity internal constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
    val date: Date,
    val url: String,
    val categories: List<String>,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val description: String,
    @Embedded
    val stats: Stats,
    val colors: List<Int>
)