package com.am.finalproject.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

object DisplayLayout {
    fun setUpVisibilityProgressBar(progressBar: ProgressBar, isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun toastMessage(context: Context, message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}