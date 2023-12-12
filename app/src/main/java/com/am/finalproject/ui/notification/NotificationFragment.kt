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
import com.am.finalproject.data.dataDummyNotification
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentNotificationBinding
import com.am.finalproject.ui.home.HomeViewModel
import org.koin.android.ext.android.inject

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

//        setUpNotificationAdapter()
        setupAdapter()

        return binding.root
    }

    private fun setupAdapter() {
        viewModel.category().observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    val adapter = FilterAdapter()
                    binding.recyclerViewNotification.adapter = adapter
                    binding.recyclerViewNotification.layoutManager = LinearLayoutManager(requireContext())
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