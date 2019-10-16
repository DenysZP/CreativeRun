package com.dm.creativerun.network.response.data

import com.dm.creativerun.domain.entity.Category

internal class CategoryData(private val id: Long, private val name: String) {

    fun mapToEntity(isPopular: Boolean = false) = Category(id, name, isPopular)
}