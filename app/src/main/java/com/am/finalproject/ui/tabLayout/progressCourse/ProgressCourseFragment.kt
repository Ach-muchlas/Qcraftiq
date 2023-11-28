package com.am.finalproject.ui.tabLayout.progressCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.home.HomePopularCourseAdapter
import com.am.finalproject.databinding.FragmentProgressCourseBinding

class ProgressCourseFragment : Fragment() {
    private var _binding: FragmentProgressCourseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressCourseBinding.inflate(inflater, container, false)
//        setUpProgressCourseAdapter()
        return binding.root
    }

//    private fun setUpProgressCourseAdapter() {
//        val adapter = HomePopularCourseAdapter(false)
//        adapter.submitList(dataDummyCourse)
//        binding.recyclerViewProgressCourse.adapter = adapter
//        binding.recyclerViewProgressCourse.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//    }


}