package com.am.finalproject.data.source

import androidx.lifecycle.liveData
import com.am.finalproject.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers

class Repository(private val apiService: ApiService) {

    fun getPopularCourse() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    data = apiService.getPopularCourse(),
                    "Success gets class popular data"
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getCategory() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    data = apiService.getCategoryCourse(),
                    "Success gets category data"
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error Occurred!!"))
        }
    }
}