package com.dm.creativerun.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dm.creativerun.database.entity.ProjectEntity

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProjects(projects: List<ProjectEntity>)

    @Query("SELECT * FROM ProjectEntity WHERE id =:id")
    suspend fun getProject(id: String): ProjectEntity
}