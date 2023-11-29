package com.am.finalproject.data.source

import androidx.lifecycle.liveData
import com.am.finalproject.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers

class Repository(
    private val apiService: ApiService,
) {
    fun getPopularCourse() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    data = apiService.getPopularCourse(),
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error Occurred!!"))
        }
    }

//    fun getPopularCourseLocalData() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            val response = apiService.getPopularCourse()
//            val data = response.data
//            val popularCourseList = data?.map { course ->
//                PopularCourseEntity(
//                    course.title,
//                    course.image,
//                    course.level,
//                    course.authorBy,
//                    course.rating,
//                    course.type,
//                    course.price,
//                    course.category.title!!
//                )
//            }
//            popularCourseDao.insertPopularCourse(popularCourseList!!)
//        } catch (e: Exception) {
//            emit(Resource.error(null, e.message ?: "Error Occurred!!"))
//        }
//        val localData: LiveData<Resource<List<PopularCourseEntity>>> =
//            popularCourseDao.getPopularCourse()
//                .map { Resource.success(it, "success get localData") }
//        emitSource(localData)
//    }


    fun getCategory() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    data = apiService.getCategoryCourse(),
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error Occurred!!"))
        }
    }
}