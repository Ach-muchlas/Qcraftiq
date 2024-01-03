package com.am.finalproject.ui.search_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Repository
import com.am.finalproject.data.source.Resource
import kotlinx.coroutines.launch

class SearchResultViewModel(private val repository: Repository) : ViewModel() {
    val searchCourse: LiveData<Resource<List<DataItemCourse>>> get() = repository.searchResult
    val filterCourse: LiveData<Resource<List<DataItemCourse>>> get() = repository.filterResult

    fun getCourseAll() = repository.getCourse()
    fun readCourseAll() = repository.readCourseALl().asLiveData()
    fun getCategoryAll() = repository.getCategory()
    fun getCourseLocalData() = repository.getCourseLocalData()
    fun searchCourseByName(query: String) {
        viewModelScope.launch {
            repository.searchCourse(query)
        }
    }

    fun filter(categoryId: String, levelCourse : String) {
        viewModelScope.launch {
            repository.filter(categoryId, levelCourse)
        }
    }


    fun searchByNameLocalData(query: String): LiveData<List<CourseEntity>> {
        return repository.searchByNameLocalData(query).asLiveData()
    }

    fun searchCourseByTitleLocalData(title: String): LiveData<List<CourseEntity>> =
        repository.searchCourseByTitleLocalData(title).asLiveData()

    fun searchCourseByTypeLocalData(typeCourse : String) : LiveData<List<CourseEntity>> =
        repository.searchCourseByTypeLocalData(typeCourse).asLiveData()

    fun filterByName(query: String) = repository.filterByType(query)

    fun searchCourseByCategory(categoryId: String) = repository.searchCourseByCategory(categoryId)
}