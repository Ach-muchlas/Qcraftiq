package com.am.finalproject.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
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

    fun showFragment(fragment: Fragment, childFragmentManager: FragmentManager) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun validateEmailEditText(editText: EditText, email: CharSequence, context: Context) {
        if (email.toString().isEmpty()) {
            editText.error = context.getString(R.string.warning_text_field_empty)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()) {
            editText.error = context.getString(R.string.warning_text_email_invalid)
        }
    }

    fun validatePasswordEditText(editText: EditText, password: CharSequence, context: Context) {
        if (password.toString().isEmpty()) {
            editText.error = context.getString(R.string.warning_text_field_empty)
        } else if (password.length > 8) {
            editText.error = context.getString(R.string.warning_text_field_less_8)
        }
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
}