package com.dm.creativerun.domain.entity

import java.util.*

data class ProjectDetails(
    val id: Long,
    val name: String,
    val date: Date,
    val url: String,
    val categories: List<String>,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val users: List<User>,
    val stats: Stats,
    val color: Int?,
    val description: String,
    val images: List<Image>
)