package com.am.finalproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.source.Repository

class HomeViewModel (private val repository: Repository): ViewModel() {
    private val _showAllItem = MutableLiveData<Boolean>()
    val showAllItem: LiveData<Boolean> get() = _showAllItem
    private val _data = MutableLiveData<List<CourseEntity>>()
    val data: LiveData<List<CourseEntity>> get() = _data

    init {
        _showAllItem.value = false
    }

    fun toggleShowAllItem() {
        _showAllItem.value = _showAllItem.value?.not()
    }

    fun getCategoryCourse() = repository.getCategory()

    fun getModule(token: String) = repository.getModule(token)

    fun getCourse() = repository.getCourse()
    fun readCourseAll() = repository.readCourseALl().asLiveData()
    fun search(query: String): LiveData<List<CourseEntity>> {
        return repository.searchCourse(query).asLiveData()
    }

}