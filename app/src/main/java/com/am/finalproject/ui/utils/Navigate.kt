package com.am.finalproject.ui.utils

import android.content.Intent
import android.content.Context

object Navigate {
	fun intentActivity(context: Context, targetActivity: Class<*>) {
		val intent = Intent(context, targetActivity)
		context.startActivity(intent)
	}

}
