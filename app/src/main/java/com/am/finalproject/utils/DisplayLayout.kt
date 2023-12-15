package com.am.finalproject.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.am.finalproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.muddz.styleabletoast.StyleableToast

object DisplayLayout {
    fun setupVisibilityProgressBar(progressBar: ProgressBar, isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun toastMessage(context: Context, message: String, isSuccess: Boolean) {
        if (isSuccess) {
            StyleableToast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT,
                R.style.MyToast_IsGreen
            ).show()
        } else {
            StyleableToast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT,
                R.style.MyToast_IsRed
            ).show()
        }
    }

    fun isValidEmail(text: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    fun isValidPhoneNumber(number: CharSequence): Boolean {
        return number.matches(Regex("\\d+"))
    }

    fun containsUpperCase(text: CharSequence): Boolean {
        for (char in text) {
            if (char.isUpperCase()) {
                return true
            }
        }
        return false
    }

    fun containsSpecialCharacter(text: CharSequence): Boolean {
        val specialCharacters = "!@#$%^&*()_-+=<>?/{}~`"
        for (char in text) {
            if (specialCharacters.contains(char)) {
                return true
            }
        }
        return false
    }

    fun buttonEnabled(button: Button, editText: EditText, context: Context) {
        if (button.isEnabled) {
            ContextCompat.getDrawable(context, R.drawable.custom_bg_button) as Drawable
        } else {
            ContextCompat.getDrawable(context, R.drawable.custom_bg_button_disable) as Drawable
        }

        val editTextEmail = editText.text
        button.isEnabled =
            editTextEmail != null && editTextEmail.isNotEmpty()
    }

    fun hideAppBar(activity: AppCompatActivity) {
        val actionBar = activity.supportActionBar
        actionBar?.hide()
    }

    fun setUpBottomNavigation(activity: Activity?, isGone: Boolean) {
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        if (isGone) {
            bottomNav?.visibility = View.GONE
        } else {
            bottomNav?.visibility = View.VISIBLE
        }
    }

}
