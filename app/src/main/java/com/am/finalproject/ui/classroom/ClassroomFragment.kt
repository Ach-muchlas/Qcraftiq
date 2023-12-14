package com.am.finalproject.ui.classroom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.R
import com.am.finalproject.adapter.classroom.CourseTrackIngAdapter
import com.am.finalproject.adapter.home.HomeCategoryAdapter
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.data.remote.DataItemTrackingClass
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentClassroomBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.home.HomeViewModel
import com.am.finalproject.utils.DisplayLayout
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class ClassroomFragment : Fragment() {
    private var _binding: FragmentClassroomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ClassroomViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val homeViewModel: HomeViewModel by inject()
    private val token by lazy {
        authViewModel.init(requireContext())
        authViewModel.getUser()?.accessToken
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassroomBinding.inflate(inflater, container, false)
        displayTopicClass()
        displayCategory()
        setUpTabLayout()

        return binding.root
    }

    private fun displayTopicClass() {
        viewModel.getDataClass(token.toString()).observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBarTopicClass, true)
                }

                Status.SUCCESS -> {
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBarTopicClass, false)
                    setupCourseTrackingAdapter(resources.data?.data)
                }

                Status.ERROR -> {
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBarTopicClass, false)
                    DisplayLayout.toastMessage(
                        requireContext(),
                        resources.message.toString(),
                        false
                    )
                }
            }
        }
    }

    private fun displayCategory() {
        homeViewModel.getCategoryLocalData().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBarCategory, true)
                }

                Status.SUCCESS -> {
                    setUpCategoryAdapter(resource.data!!)
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBarCategory, false)
                }

                Status.ERROR -> {
                    DisplayLayout.setupVisibilityProgressBar(binding.progressBarCategory, false)
                    DisplayLayout.toastMessage(
                        requireContext(),
                        " Error ${resource.message}",
                        false
                    )
                }
            }

        }
    }

    private fun setUpTabLayout() {
        val tabLayout = binding.tabLayoutClass
        tabLayout.addTab(tabLayout.newTab().setText("All"))
        tabLayout.addTab(tabLayout.newTab().setText("In Progress"))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.done)))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.text.toString()) {
                    "All" -> {
                        displayTopicClass()
                    }
                    "In Progress" -> {
                        "PROGRESS".progressTopicClass()
                    }
                    "Done" -> {}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun String.progressTopicClass() {
        viewModel.filterByStatus(this, token.toString())
            .observe(viewLifecycleOwner) { resources ->
                when (resources.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        setupCourseTrackingAdapter(resources.data)
                    }

                    Status.ERROR -> {}
                }
            }

    }

    private fun setupCourseTrackingAdapter(data: List<DataItemTrackingClass>?) {
        val adapter = CourseTrackIngAdapter()
        adapter.apply {
            submitList(data)
            binding.recyclerViewTopicClass.adapter = this
        }
        binding.recyclerViewTopicClass.layoutManager = LinearLayoutManager(requireContext())

    }

    /*The function is used to set up a data adapter for popular courses. */
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpCategoryAdapter(data: List<CategoryEntity>) {
        val adapter = HomeCategoryAdapter()
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.submitList(data)
        binding.textViewSeeAllCategory.setOnClickListener {
            homeViewModel.showAllItem.observe(viewLifecycleOwner) { showALlItem ->
                adapter.showAllItems = showALlItem
                adapter.notifyDataSetChanged()
            }
            homeViewModel.toggleShowAllItem()
        }
    }
}