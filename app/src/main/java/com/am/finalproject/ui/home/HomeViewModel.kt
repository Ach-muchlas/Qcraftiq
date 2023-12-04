package com.am.finalproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.finalproject.data.source.Repository

class HomeViewModel (private val repository: Repository): ViewModel() {
    private val _showAllItem = MutableLiveData<Boolean>()
    val showAllItem: LiveData<Boolean> get() = _showAllItem

    init {
        _showAllItem.value = false
    }

    fun toggleShowAllItem() {
        _showAllItem.value = _showAllItem.value?.not()
    }

    fun getPopularCourse() = repository.getPopularCourse()


    fun getCategoryCourse() = repository.getCategory()
}