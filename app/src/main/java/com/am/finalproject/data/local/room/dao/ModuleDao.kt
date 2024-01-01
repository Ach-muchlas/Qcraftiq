package com.am.finalproject.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.data.local.entity.ModuleEntity

@Dao
interface ModuleDao {
    @Query("SELECT * FROM module ORDER BY id ASC")
    fun getModuleFromLocalData(): LiveData<List<ModuleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertModule(popularCourse: List<ModuleEntity>)
}