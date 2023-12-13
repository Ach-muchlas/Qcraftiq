package com.am.finalproject.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.filter.FilterAdapter
import com.am.finalproject.adapter.notification.NotificationAdapter
import com.am.finalproject.data.Database
import com.am.finalproject.data.remote.NotificationResponse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentNotificationBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.home.HomeViewModel
import com.am.finalproject.utils.DisplayLayout
import org.koin.android.ext.android.inject

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by inject()
    private val viewModel: NotificationViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        displayNotification()
//        setUpNotificationAdapter()
//        setupAdapter()

        return binding.root
    }

    /*fungsi ini digunakan untuk filter bottom sheet*/
    private fun setupAdapter() {
        homeViewModel.category().observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    val adapter = FilterAdapter()
                    binding.recyclerViewNotification.adapter = adapter
                    binding.recyclerViewNotification.layoutManager =
                        LinearLayoutManager(requireContext())
                    val category = resources.data
                    if (category != null) {
                        val data = Database.getItem(category)
                        adapter.updateList(data)
                    }
                }

                Status.ERROR -> {}
            }
        }
    }

    private fun displayNotification() {
        authViewModel.init(requireContext())
        val token = authViewModel.getUser()?.accessToken
        viewModel.notificationUser(token.toString()).observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBar, true)
                }

                Status.SUCCESS -> {
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBar, false)
                    setUpNotificationAdapter(resources.data)
                }

                Status.ERROR -> {
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBar, false)
                    DisplayLayout.toastMessage(
                        requireContext(),
                        resources.message.toString(),
                        false
                    )
                }
            }
        }
    }

    private fun setUpNotificationAdapter(data: NotificationResponse?) {
        val adapter = NotificationAdapter()
        adapter.submitList(data?.data)
        binding.recyclerViewNotification.adapter = adapter
        binding.recyclerViewNotification.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}