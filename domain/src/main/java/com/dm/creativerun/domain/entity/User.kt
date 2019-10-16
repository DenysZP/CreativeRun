package com.dm.creativerun.domain.entity

data class User(
    val id: Long,
    val name: String,
    val location: String,
    val company: String,
    val occupation: String,
    val url: String,
    val imageUrl: String?,
    val thumbnailUrl: String?
)