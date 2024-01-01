package com.am.finalproject.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.am.finalproject.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CourseDao {

    @Query("SELECT * FROM course ORDER BY title DESC")
    fun getCourseFromLocalData(): LiveData<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(popularCourse: List<CourseEntity>)

    @Query("SELECT * FROM course WHERE categoryTitle LIKE :categoryTitle ")
    fun searchCourseByCategoryTitle(categoryTitle: String):Flow<List<CourseEntity>>

    @Query("SELECT * FROM course WHERE title LIKE :titleCourse ")
    fun searchCourseByTitle(titleCourse: String):Flow<List<CourseEntity>>

    @Query("SELECT * FROM course WHERE type LIKE :typeCourse ")
    fun searchCourseByType(typeCourse: String):Flow<List<CourseEntity>>
    @Query("SELECT * FROM course ORDER BY title DESC")
    fun readCourseAll() : Flow<List<CourseEntity>>

    @Query("DELETE FROM course")
    fun delete()
}