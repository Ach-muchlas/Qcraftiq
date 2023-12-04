package com.am.finalproject.ui.classroom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.am.finalproject.adapter.home.HomeCategoryAdapter
import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentClassroomBinding
import com.am.finalproject.ui.home.HomeViewModel
import com.am.finalproject.ui.tabLayout.progressCourse.ProgressCourseFragment
import com.am.finalproject.utils.DisplayLayout.setupVisibilityProgressBar
import com.am.finalproject.utils.DisplayLayout.showFragment
import com.am.finalproject.utils.DisplayLayout.toastMessage
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class ClassroomFragment : Fragment() {
    private var _binding: FragmentClassroomBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassroomBinding.inflate(inflater, container, false)
//        setupTabLayout()
        displayCategory()
        return binding.root
    }


    private fun displayCategory() {
        homeViewModel.getCategoryCourse().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    setupVisibilityProgressBar(binding.progressBarCategory, true)
                }

                Status.SUCCESS -> {
                    setUpCategoryAdapter(resource.data)
                    setupVisibilityProgressBar(binding.progressBarCategory, false)
                }

                Status.ERROR -> {
                    setupVisibilityProgressBar(binding.progressBarCategory, false)
                    toastMessage(requireContext(), "${resource.message}")
                }
            }
        }
    }

//    private fun setupTabLayout() {
//        val tabLayout = binding.tabLayoutClass
//        val tabTitle = listOf("All", "In Progress", "Done")
//        for (title in tabTitle) {
//            val tab = tabLayout.newTab().setText(title)
//            tabLayout.addTab(tab)
//        }
//
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                tab.let {
//                    val fragment = ProgressCourseFragment()
//                    showFragment(fragment, childFragmentManager)
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//            }
//
//        })
//        val firsFragment = ProgressCourseFragment()
//        showFragment(firsFragment, childFragmentManager)
//    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpCategoryAdapter(data: CategoryResponse?) {
        val adapter = HomeCategoryAdapter()
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.submitList(data?.data)
        binding.textViewSeeAllCategory.setOnClickListener {
            homeViewModel.showAllItem.observe(viewLifecycleOwner) { showALlItem ->
                adapter.showAllItems = showALlItem
                adapter.notifyDataSetChanged()
            }
            homeViewModel.toggleShowAllItem()
        }
    }

}