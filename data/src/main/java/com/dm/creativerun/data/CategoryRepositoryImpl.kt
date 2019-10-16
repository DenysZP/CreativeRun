package com.dm.creativerun.data

import com.dm.creativerun.database.AppDatabase
import com.dm.creativerun.database.adapter.CategoryAdapter
import com.dm.creativerun.domain.entity.Category
import com.dm.creativerun.domain.repository.CategoryRepository
import com.dm.creativerun.network.NetworkClient

class CategoryRepositoryImpl(
    private val database: AppDatabase,
    private val networkClient: NetworkClient
) : CategoryRepository {

    override suspend fun getCategories(): List<Category> {
        val categoryDao = database.categoryDao()
        val localData = categoryDao.getCategories().map { CategoryAdapter.fromStorage(it) }
        return localData.ifEmpty {
            networkClient.categoryService.getCategoriesAsync().await().mapToEntities()
                .also { remoteData ->
                    val adaptedList = remoteData.map { CategoryAdapter.toStorage(it) }
                    categoryDao.saveCategories(adaptedList)
                }
        }
    }
}