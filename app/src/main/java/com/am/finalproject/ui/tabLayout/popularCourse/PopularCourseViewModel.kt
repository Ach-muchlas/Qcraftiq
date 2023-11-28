package com.am.finalproject.ui.tabLayout.popularCourse

import androidx.lifecycle.ViewModel
import com.am.finalproject.data.source.Repository

class PopularCourseViewModel(private val repository: Repository) : ViewModel() {
    fun getPopularCourse() = repository.getPopularCourse()

    fun getCategory() = repository.getCategory()
}