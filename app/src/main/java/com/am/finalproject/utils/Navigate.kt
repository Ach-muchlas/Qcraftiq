package com.am.finalproject.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.am.finalproject.R

object Navigate {
    fun navigateToDestination(destination: Destination, navController: NavController) {
        navController.let {
            when (destination) {
                Destination.COURSE_TO_SEARCH -> it.navigate(R.id.action_navigation_course_to_searchResultFragment)
                Destination.HOME_TO_SEARCH -> it.navigate(R.id.action_navigation_home_to_searchResultFragment)
                Destination.ACCOUNT_TO_HISTORY_PAYMENT -> it.navigate(R.id.action_navigation_account_to_paymentHistoryFragment)
                Destination.ACCOUNT_TO_CHANGE_PASSWORD -> it.navigate(R.id.action_navigation_account_to_changePasswordFragment)
                Destination.ACCOUNT_TO_PROFILE -> it.navigate(R.id.action_navigation_account_to_myProfileFragment)
            }
        }
    }

    fun intentActivity(context: Context, targetActivity: Class<*>) {
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
    }

    fun intentActivityUseFinish(context: Context, targetActivity: Class<*>) {
        val intent = Intent(context, targetActivity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        if (context is AppCompatActivity) {
            context.finish()
        }
    }
}
