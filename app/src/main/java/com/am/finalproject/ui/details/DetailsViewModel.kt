package com.am.finalproject.ui.details

import androidx.lifecycle.ViewModel
import com.am.finalproject.data.source.Repository

class DetailsViewModel(private val repository: Repository) : ViewModel() {
    fun getDetailByIdCourse(id : String) = repository.searchCourseById(id)
}