package com.am.finalproject.ui.tabLayout.progressCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.classroom.CourseTrackIngAdapter
import com.am.finalproject.data.remote.CourseResponse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentProgressCourseBinding
import com.am.finalproject.ui.home.HomeViewModel
import com.am.finalproject.utils.DisplayLayout.setupVisibilityProgressBar
import com.am.finalproject.utils.DisplayLayout.toastMessage
import org.koin.android.ext.android.inject

class ProgressCourseFragment : Fragment() {
    private var _binding: FragmentProgressCourseBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressCourseBinding.inflate(inflater, container, false)
        displayProgressClass()
        return binding.root
    }

    private fun displayProgressClass() {
        homeViewModel.getPopularCourse().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    setupVisibilityProgressBar(binding.progressBarClass, true)
                }

                Status.SUCCESS -> {
                    setupVisibilityProgressBar(binding.progressBarClass, false)
                    setupProgressCourseAdapter(resource.data)
                }

                Status.ERROR -> {
                    setupVisibilityProgressBar(binding.progressBarClass, false)
                    toastMessage(requireContext(), "${resource.message}")
                }
            }
        }
    }

    private fun setupProgressCourseAdapter(data: CourseResponse?) {
        val adapter = CourseTrackIngAdapter()
        adapter.submitList(data?.data)
        binding.recyclerViewProgressCourse.adapter = adapter
        binding.recyclerViewProgressCourse.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
}