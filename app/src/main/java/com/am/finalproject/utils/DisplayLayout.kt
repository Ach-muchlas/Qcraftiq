package com.am.finalproject.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.am.finalproject.R

object DisplayLayout {
    fun setupVisibilityProgressBar(progressBar: ProgressBar, isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun toastMessage(context: Context, message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showFragment(fragment: Fragment, childFragmentManager: FragmentManager) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}