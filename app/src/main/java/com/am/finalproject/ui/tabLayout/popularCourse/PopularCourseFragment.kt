package com.am.finalproject.ui.tabLayout.popularCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.home.HomePopularCourseAdapter
import com.am.finalproject.data.remote.CourseResponse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentPopularCourseBinding
import com.am.finalproject.ui.home.HomeViewModel
import com.am.finalproject.utils.DisplayLayout.setupVisibilityProgressBar
import com.am.finalproject.utils.DisplayLayout.toastMessage
import org.koin.android.ext.android.inject

class PopularCourseFragment : Fragment() {
    private var _binding: FragmentPopularCourseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularCourseBinding.inflate(inflater, container, false)
        displayPopularCourse()
        return binding.root
    }

    private fun displayPopularCourse() {
        viewModel.getPopularCourse().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    setupVisibilityProgressBar(
                        binding.progressBar,
                        true
                    )
                }

                Status.SUCCESS -> {
                    setupVisibilityProgressBar(binding.progressBar, false)
                    setUpPopularCourseAdapter(resource.data)
                }

                Status.ERROR -> {
                    setupVisibilityProgressBar(binding.progressBar, false)
                    toastMessage(requireContext(), message = "Error : ${resource.message}")
                }
            }
        }
    }

//    private fun displayPopularCourseLocalData(){
//        viewModel.getPopularCourseLocalData().observe(viewLifecycleOwner){resource ->
//            when(resource.status){
//                Status.LOADING -> {
//                    setUpVisibilityProgressBar(
//                        binding.progressBar,
//                        true
//                    )
//                }
//                Status.SUCCESS -> {
//                    setUpVisibilityProgressBar(binding.progressBar, false)
//                    setUpPopularCourseAdapter(resource.data!!)
//                    toastMessage(requireContext(), message = "${resource.message}")
//                }
//                Status.ERROR -> {
//                    setUpVisibilityProgressBar(binding.progressBar, false)
//                    toastMessage(requireContext(), message = "Error : ${resource.message}")
//                }
//
//
//            }
//
//        }
//    }

    private fun setUpPopularCourseAdapter(data: CourseResponse?) {
        val adapter = HomePopularCourseAdapter()
        adapter.submitList(data?.data)
        binding.recyclerViewPopularCourse.adapter = adapter
        binding.recyclerViewPopularCourse.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

}