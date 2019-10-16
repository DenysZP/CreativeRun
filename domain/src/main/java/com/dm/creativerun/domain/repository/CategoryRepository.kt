package com.dm.creativerun.domain.repository

import com.dm.creativerun.domain.entity.Category

interface CategoryRepository {

    suspend fun getCategories(): List<Category>
}