package com.dm.creativerun.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dm.creativerun.database.dao.CategoryDao
import com.dm.creativerun.database.dao.ProjectDao
import com.dm.creativerun.database.entity.CategoryEntity
import com.dm.creativerun.database.entity.ProjectEntity

@Database(
    entities = [
        CategoryEntity::class,
        ProjectEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "App.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun categoryDao(): CategoryDao

    abstract fun projectDao(): ProjectDao
}