package com.am.finalproject.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.local.room.dao.CategoryDao
import com.am.finalproject.data.local.room.dao.CourseDao

@Database(
    entities = [CourseEntity::class, CategoryEntity::class],
    version = 3,
    exportSchema = false,
)
abstract class DatabaseDb : RoomDatabase() {
    abstract fun CourseDao(): CourseDao
    abstract fun CategoryDao() : CategoryDao
    companion object {
        @Volatile
        private var instance: DatabaseDb? = null
        fun getInstance(context: Context): DatabaseDb =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseDb::class.java, "News.db"
                ).fallbackToDestructiveMigration().build()
            }
    }
}