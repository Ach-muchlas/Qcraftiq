package com.am.finalproject.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.local.room.dao.CourseDao
import com.am.finalproject.data.remote.LoginBody
import com.am.finalproject.data.remote.RegisterBody
import com.am.finalproject.data.remote.RegisterBodyWithOTP
import com.am.finalproject.data.retrofit.ApiService
import com.am.finalproject.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiService: ApiService,
    private val courseDao: CourseDao,
    private val appExecutor: AppExecutors
) {
    fun loginUser(emailOrPhone: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val user = LoginBody(emailOrPhone, password)
            emit(Resource.success(apiService.login(user)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    fun registerUser(name: String, email: String, phone: String, password: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val user = RegisterBody(name, email, phone, password)
                emit(Resource.success(apiService.register(user)))
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }


    fun sendOTP(name: String, email: String, phone: String, password: String, otp: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val user = RegisterBodyWithOTP(name, email, phone, password, otp)
                emit(Resource.success(apiService.sendOTP(user)))
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun resendOTP(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.resendOTP(email)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

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

    fun readCourseALl(): Flow<List<CourseEntity>> {
        return courseDao.readCourseAll()
    }

//    fun getCategory2(): LiveData<Resource<List<CategoryEntity>>> = liveData {
//        emit(Resource.loading(null))
//        try {
//            val response = apiService.getCategoryCourse()
//            val data = response.data
//            val list = data.map { category ->
//                CategoryEntity(
//                    category.id,
//                    category.image,
//                    category.title
//                )
//            }
//            categoryDao.insertCategory(list)
//        } catch (exception: Exception) {
//            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
//        }
//        val localData: LiveData<Resource<List<CategoryEntity>>> =
//            categoryDao.getCategoryFromLocalData().map { Resource.success(it) }
//        emitSource(localData)
//    }

    fun getCourse(): LiveData<Resource<List<CourseEntity>>> = liveData {
        emit(Resource.loading(null))
        try {
            val responseCourse = apiService.getPopularCourse()
            val data = responseCourse.data
            appExecutor.diskIO.execute {
                val list = data.map { course ->
                    CourseEntity(
                        course.title,
                        course.image,
                        course.level,
                        course.authorBy,
                        course.rating,
                        course.price,
                        course.category.title,
                        1
                    )
                }
                Log.e("SIMPLE", "list : $list")
                courseDao.insertCourse(list)
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message.toString()))
        }
        val localData: LiveData<Resource<List<CourseEntity>>> =
            courseDao.getCourseFromLocalData().map { Resource.success(it) }
        emitSource(localData)
    }

    fun searchCourse(query: String): Flow<List<CourseEntity>> {
        return courseDao.searchCourse(query)
    }

    fun getModule(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.getModules("Bearer $token")))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

}