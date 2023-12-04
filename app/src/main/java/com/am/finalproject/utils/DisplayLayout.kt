package com.am.finalproject.utils

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.am.finalproject.R

object DisplayLayout {
    fun setupVisibilityProgressBar(progressBar: ProgressBar, isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun toastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showFragment(container: Int, fragment: Fragment, childFragmentManager: FragmentManager) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
}