package com.am.finalproject.ui.notification

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.notification.NotificationAdapter
import com.am.finalproject.data.dataDummyNotification
import com.am.finalproject.databinding.FragmentNotificationBinding

@RequiresApi(Build.VERSION_CODES.O)
class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        setUpNotificationAdapter()
        return binding.root
    }

    private fun setUpNotificationAdapter() {
        val adapter = NotificationAdapter()
        adapter.submitList(dataDummyNotification)
        binding.recyclerViewNotification.adapter = adapter
        binding.recyclerViewNotification.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}