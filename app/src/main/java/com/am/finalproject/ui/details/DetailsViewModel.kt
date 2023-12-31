package com.am.finalproject.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.am.finalproject.data.source.Repository
import com.am.finalproject.data.source.Resource
import kotlinx.coroutines.Dispatchers

class DetailsViewModel(private val repository: Repository) : ViewModel() {
    fun getDetailByIdCourse(id : String) = repository.searchCourseById(id)

    fun getDetailVideoByIdCourse (token:String, courseId : String)=  liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.getDetailById(token, courseId)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}