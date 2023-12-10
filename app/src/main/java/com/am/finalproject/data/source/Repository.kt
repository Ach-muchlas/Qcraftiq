package com.am.finalproject.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.local.room.dao.CategoryDao
import com.am.finalproject.data.local.room.dao.CourseDao
import com.am.finalproject.data.remote.DataItemCourse
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
    private val categoryDao: CategoryDao,
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

    fun resetPassword(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.resetPassword(email)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun readCourseALl(): Flow<List<CourseEntity>> {
        return courseDao.readCourseAll()
    }

    fun getCategoryLocalData(): LiveData<Resource<List<CategoryEntity>>> = liveData {
        emit(Resource.loading(null))
        try {
            val response = apiService.getCategoryCourse()
            val data = response.data
            appExecutor.diskIO.execute {
                val list = data.map { category ->
                    CategoryEntity(
                        category.id,
                        category.image,
                        category.title
                    )
                }
                categoryDao.delete()
                categoryDao.insertCategory(list)
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
        val localData: LiveData<Resource<List<CategoryEntity>>> =
            categoryDao.getCategoryFromLocalData().map { Resource.success(it) }
        emitSource(localData)
    }

    fun getCourseLocalData(): LiveData<Resource<List<CourseEntity>>> = liveData {
        emit(Resource.loading(null))
        try {
            val responseCourse = apiService.getPopularCourse()
            val data = responseCourse.data
            appExecutor.diskIO.execute {
                val list = data.map { course ->
                    val timeModule = course.module?.sumOf { it.time ?: 0 }
                    val sizeModule = course.module?.size
                    CourseEntity(
                        course.title,
                        course.image,
                        course.level,
                        course.authorBy,
                        course.rating,
                        course.price,
                        course.category.title,
                        timeModule ?: 0,
                        sizeModule ?: 0
                    )
                }
                courseDao.delete()
                courseDao.insertCourse(list)
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message.toString()))
        }
        val localData: LiveData<Resource<List<CourseEntity>>> =
            courseDao.getCourseFromLocalData().map { Resource.success(it) }
        emitSource(localData)
    }

    fun getCourse() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.getPopularCourse()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun searchByNameLocalData(query: String): Flow<List<CourseEntity>> {
        return courseDao.searchCourse(query)
    }

    private val _searchResult = MutableLiveData<Resource<List<DataItemCourse>>>()
    val searchResult: LiveData<Resource<List<DataItemCourse>>>
        get() = _searchResult

    suspend fun searchCourse(query: String) {
        _searchResult.value = Resource.loading(null)
        try {
            val response = apiService.getPopularCourse()
            val allCourse = response.data
            val filtered = allCourse.filter { it.title.contains(query, ignoreCase = true) }
            _searchResult.value = Resource.success(filtered)
        } catch (exception: Exception) {
            _searchResult.value = Resource.error(null, exception.message ?: "Error Occurred!!")
        }
    }

    fun searchByType(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val response = apiService.getPopularCourse()
        try {
            val allCourse = response.data
            val filtered = allCourse.filter { it.type.contains(query, ignoreCase = true) }
            emit(Resource.success(filtered))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }
}