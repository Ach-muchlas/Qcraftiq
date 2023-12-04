package com.am.finalproject.data.di

import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.retrofit.ApiConfig
import com.am.finalproject.data.source.Repository
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val databaseModule
        get() = module {
            /*api service*/
            single { ApiConfig.getApiService() }
//
//            /*database db*/
//            single { DatabaseDb.getInstance(get()) }
//
//            /*popular course dao*/
//            factory { get<DatabaseDb>().PopularCourseDao() }

            factory { Repository(get()) }
        }
    val uiModule
        get() = module {
            viewModel { HomeViewModel(get()) }
            viewModel { AuthViewModel(get()) }
        }
}