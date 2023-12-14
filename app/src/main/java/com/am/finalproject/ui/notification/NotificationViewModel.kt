package com.am.finalproject.ui.notification

import androidx.lifecycle.ViewModel
import com.am.finalproject.data.source.Repository

class NotificationViewModel(private val repository: Repository) : ViewModel() {
    fun notificationUser(token : String) = repository.getNotification(token)
}