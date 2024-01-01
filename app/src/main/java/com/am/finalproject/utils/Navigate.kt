package com.am.finalproject.utils

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.am.finalproject.R

object Navigate {
    fun navigateToDestination(destination: Destination, navController: NavController) {
        navController.let {
            when (destination) {
                Destination.COURSE_TO_SEARCH -> it.navigate(R.id.action_navigation_course_to_searchResultFragment)
            }
        }
    }

    fun intentActivity(context: Context, targetActivity: Class<*>) {
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
    }
}
