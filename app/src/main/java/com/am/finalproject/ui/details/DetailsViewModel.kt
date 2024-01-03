package com.am.finalproject.ui.details

import android.content.Context
import androidx.lifecycle.ViewModel
import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.source.Repository

class DetailsViewModel(private val repository: Repository) : ViewModel() {
    fun getDetailByIdCourse(id : String) = repository.searchCourseById(id)

    private lateinit var sharedpref: UserPreferences

    fun init(context: Context) {
        sharedpref = UserPreferences(context)
    }
}