package com.am.finalproject.data.di

import com.am.finalproject.data.retrofit.ApiConfig
import com.am.finalproject.data.source.Repository
import com.am.finalproject.ui.tabLayout.popularCourse.PopularCourseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val databaseModule
        get() = module {
            single { ApiConfig.getApiService() }
            factory { Repository(get()) }
        }
    val uiModule
        get() = module {
            viewModel { PopularCourseViewModel(get()) }
        }
}