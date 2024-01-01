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
import com.am.finalproject.data.remote.Payment
import com.am.finalproject.data.remote.PaymentBody
import com.am.finalproject.data.remote.RegisterBody
import com.am.finalproject.data.remote.RegisterBodyWithOTP
import com.am.finalproject.data.remote.UserBody
import com.am.finalproject.data.retrofit.ApiService
import com.am.finalproject.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

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
            val response = apiService.login(user)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
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
                val response = apiService.register(user)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessage = errorResponse.getString("message")
                        emit(Resource.error(null, errorMessage))
                    }
                }
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
                val response = apiService.sendOTP(user)

                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessage = errorResponse.getString("message")
                        emit(Resource.error(null, errorMessage))
                    }
                }

            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    /*fungsi ini digunakan untuk mengirim kembali otp pada saat sudah melebihi batas 1 menit*/
    fun resendOTP(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.resendOTP(email)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    /*fungsi ini digunakan untuk reset password user*/
    /*ketika user lupa password*/
    fun resetPassword(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.resetPassword(email)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
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
                        course.id,
                        course.image,
                        course.level,
                        course.authorBy,
                        course.rating,
                        course.price,
                        course.category.title,
                        timeModule ?: 0,
                        sizeModule ?: 0,
                        course.type
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
        return courseDao.searchCourseByCategoryTitle(query)
    }

    fun searchCourseByTitleLocalData(title : String) : Flow<List<CourseEntity>>{
        return courseDao.searchCourseByTitle(title)
    }

    fun searchCourseByTypeLocalData(typeCourse : String) : Flow<List<CourseEntity>>{
        return courseDao.searchCourseByType(typeCourse)
    }


    /*This variable is used as a container for search results*/
    private val _searchResult = MutableLiveData<Resource<List<DataItemCourse>>>()
    val searchResult: LiveData<Resource<List<DataItemCourse>>>
        get() = _searchResult

    /*This variable is used as a container for search results*/
    private val _filterResult = MutableLiveData<Resource<List<DataItemCourse>>>()
    val filterResult: LiveData<Resource<List<DataItemCourse>>>
        get() = _filterResult

    /*fungsi ini digunakan untuk mencari course pada search result fragment*/
    /*search ini melakukan filter sesuai nama course yang diinputkan user*/
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

    suspend fun filter(query1: String) {
        _filterResult.value = Resource.loading(null)
        try {
            val response = apiService.getPopularCourse()
            val allCourse = response.data
            val filtered = allCourse.filter {
                it.category.id.contains(query1, ignoreCase = true)
            }
            _filterResult.value = Resource.success(filtered)
        } catch (exception: Exception) {
            _filterResult.value = Resource.error(null, exception.message ?: "Error Occurred!!")
        }
    }


    fun searchCourseByCategory(categoryId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getPopularCourse()
            val allCategory = response.data
            val filtered =
                allCategory.filter { it.category.title.contains(categoryId, ignoreCase = true) }
            emit(Resource.success(filtered))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    fun filterByType(query: String) = liveData(Dispatchers.IO) {
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

    fun filterByStatus(query: String, token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getTrackingClass("Bearer $token")
            val allCourse = response.body()?.data
            val filtered =
                allCourse?.filter { it.status.toString().contains(query, ignoreCase = true) }
            emit(Resource.success(filtered))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun searchCourseById(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getPopularCourse()
            val allCourse = response.data
            val filteredById = allCourse.filter { it.id.contains(id, ignoreCase = true) }
            emit(Resource.success(filteredById))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }

    }

    fun searchCourseByNameInClass(query: String, token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getTrackingClass("Bearer $token")
            val allCourse = response.body()?.data
            val filtered =
                allCourse?.filter { it.status.toString().contains(query, ignoreCase = true) }
            emit(Resource.success(filtered))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }


    /* fungsi ini digunakan untuk get notification */
    fun getNotification(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getNotification("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getTrackingClass(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getTrackingClass("Bearer $token")
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getFilterTrackingClass(token: String, statusTrackingClass: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val response = apiService.getTrackingClass("Bearer $token")
                val allData = response.body()?.data
                val filtered =
                    allData?.filter { it.status!!.contains(statusTrackingClass, ignoreCase = true) }
                emit(Resource.success(filtered))

            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }


    fun getCurrentUser(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getCurrentUser("Bearer $token")

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun updateUser(
        token: String,
        name: String,
        email: String,
        phone: String,
        country: String,
        city: String,
        image: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val user = UserBody(name, email, phone, country, city, image)
            val response = apiService.updateUser("Bearer $token", user)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getPaymentHistory(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getTrackingClass("Bearer $token")

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun paymentOrders(
        token: String,
        expiredDate: String,
        cvv: Int,
        amount: Int,
        cardName: String,
        cardNumber: String,
        courseId: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val paymentBody =
                PaymentBody(Payment(expiredDate, cvv, amount, cardName, cardNumber), courseId)
            val response = apiService.orderCourse("Bearer $token", paymentBody)

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getHistoryOrders(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getHistoryOrders("Bearer $token")

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = JSONObject(it.string())
                    val errorMessage = errorResponse.getString("message")
                    emit(Resource.error(null, errorMessage))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))

        }
    }
}