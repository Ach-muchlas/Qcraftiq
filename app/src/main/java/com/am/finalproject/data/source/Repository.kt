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

    /*Authentication*/
    /*fungsi ini digunakan untuk login user*/
    fun loginUser(emailOrPhone: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val user = LoginBody(emailOrPhone, password)
            emit(Resource.success(apiService.login(user)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    /*fungsi ini digunakan untuk register user*/
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

    /*fungsi ini digunakan untuk send otp setelah register user*/
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

    /*fungsi ini digunakan untuk mengirim kembali otp pada saat sudah melebihi batas 1 menit*/
    fun resendOTP(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.resendOTP(email)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    /*fungsi ini digunakan untuk reset password user*/
    /*ketika user lupa password*/
    fun resetPassword(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.resetPassword(email)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    /*User management*/
    fun changePassword(password: String, newPassword: String, token : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.changePasswordUser("Bearer $token", password, newPassword)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    /*Course*/
    /*fungsi ini digunkaan untuk read ulang data course dari local */
    fun readCourseALl(): Flow<List<CourseEntity>> {
        return courseDao.readCourseAll()
    }

    /*fungsi ini digunakan untuk mendapatkan data course*/
    /*namun fungsi ini menerapkan mengambil data dari internet ketika ada internet*/
    /*dan ketika tidak ada internet maka akan mengambil data local*/
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

    /*fungsi ini digunakan untuk mendapatkan data course dari internet*/
    fun getCourse() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.getPopularCourse()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    /*Category*/
    /*fungsi ini digunakan untuk mendapatkan data category*/
    /*namun fungsi ini menerapkan mengambil data dari internet ketika ada internet*/
    /*dan ketika tidak ada internet maka akan mengambil data local*/
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

    /*fungsi ini digunakan untuk mendapatkan data category dari internet*/
    fun getCategory() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.getCategoryCourse()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    /*Search*/
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

    fun filter(query1: String? = null, query2: String? = null) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val response = apiService.getPopularCourse()
        try {
            val allCourse = response.data
            val filtered = allCourse.filter {
                it.category.id.contains(
                    query1.toString(),
                    ignoreCase = true
                ) && it.level.contains(query2.toString(), ignoreCase = true)
            }
            emit(Resource.success(filtered))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getNotification(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val response = apiService.getNotification("Bearer $token")
        try {
            emit(Resource.success(response))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }
}