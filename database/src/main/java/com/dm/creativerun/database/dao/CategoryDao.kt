package com.dm.creativerun.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dm.creativerun.database.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategories(categoryList: List<CategoryEntity>)

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getCategories(): List<CategoryEntity>
}