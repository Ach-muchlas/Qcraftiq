package com.am.finalproject.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors


object AppExecutors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()
}