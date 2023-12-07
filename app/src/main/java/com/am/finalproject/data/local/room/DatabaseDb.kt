package com.am.finalproject.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.local.entity.ModuleEntity
import com.am.finalproject.data.local.room.dao.CategoryDao
import com.am.finalproject.data.local.room.dao.CourseDao
import com.am.finalproject.data.local.room.dao.ModuleDao

@Database(
    entities = [CourseEntity::class],
    version = 1,
)
abstract class DatabaseDb : RoomDatabase() {
    abstract fun CourseDao(): CourseDao
    companion object {
        @Volatile
        private var instance: DatabaseDb? = null
        fun getInstance(context: Context): DatabaseDb =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseDb::class.java, "News.db"
                ).build()
            }
    }
}