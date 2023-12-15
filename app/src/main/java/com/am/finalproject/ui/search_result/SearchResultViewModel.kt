package com.am.finalproject.ui.search_result

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

    fun getCourseAll() = repository.getCourse()

    fun getCategoryAll() = repository.getCategory()

    fun searchCourseByName(query: String) {
        viewModelScope.launch {
            repository.searchCourse(query)
        }
    }

    fun searchByNameLocalData(query: String): LiveData<List<CourseEntity>> {
        return repository.searchByNameLocalData(query).asLiveData()
    }

    fun filterByName(query: String) = repository.filterByName(query)


    fun filter(query1: String? = null, query2: String? = null) {
        viewModelScope.launch {
            repository.filter(query1, query2)
        }
    }
}