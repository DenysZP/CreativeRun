package com.dm.creativerun.network.response

import com.dm.creativerun.domain.entity.Category
import com.dm.creativerun.network.response.data.CategoryData

class CategoriesResponse internal constructor(
    private val fields: List<CategoryData>,
    private val popular: List<CategoryData>
) {

    fun mapToEntities(): List<Category> {
        return fields.map {
            val isPopular = popular.contains(it)
            it.mapToEntity(isPopular)
        }
    }
}