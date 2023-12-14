package com.am.finalproject.ui.classroom

import androidx.lifecycle.ViewModel
import com.am.finalproject.data.source.Repository

class ClassroomViewModel(private val repository: Repository) : ViewModel() {
    fun getDataClass(token: String) = repository.getTrackingClass(token)
    fun filterByStatus(query: String, token: String) = repository.filterByStatus(query, token)
}