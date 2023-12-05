package com.am.finalproject.utils

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.am.finalproject.R

object Navigate {
//    fun navigateToDestination(destination: Destination, navController: NavController) {
//        navController.let {
//            when (destination) {
//                Destination.LOGIN_TO_REGISTER -> it.navigate(R.id.action_loginFragment_to_registerFragment)
//            }
//        }
//    }

    fun intentActivity(context: Context, targetActivity: Class<*>) {
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
    }
}
