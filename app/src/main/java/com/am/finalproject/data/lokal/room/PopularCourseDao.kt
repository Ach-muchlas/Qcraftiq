package com.am.finalproject.data.lokal.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


//@Dao
//interface PopularCourseDao {
//
//    @Query("SELECT * FROM popular_course ORDER BY title DESC")
//    fun getPopularCourse(): LiveData<List<PopularCourseEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertPopularCourse(popularCourse: List<PopularCourseEntity>)
//
//}