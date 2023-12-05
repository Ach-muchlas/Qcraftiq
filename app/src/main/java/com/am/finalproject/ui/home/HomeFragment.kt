package com.am.finalproject.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.home.HomeCategoryAdapter
import com.am.finalproject.adapter.home.HomePopularCourseAdapter
import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.remote.CourseResponse
import com.am.finalproject.data.remote.DataItemCategory
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentHomeBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.utils.DisplayLayout.setupVisibilityProgressBar
import com.am.finalproject.utils.DisplayLayout.toastMessage
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private lateinit var sharedpref : UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        displayCategory()
        sharedpref = UserPreferences(requireContext())
        binding.textViewSeeAllPopularCourse.setOnClickListener {
            sharedpref.clearUser()
        }
        return binding.root
    }

    private fun setUpTabLayout(data: List<DataItemCategory?>?) {
        val tabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText(""))
        data?.forEach {
            val tabTitle = listOf( it?.title)
            Log.e("CHECK", "title : $tabTitle")
            for (title in tabTitle) {
                val tab = tabLayout.newTab().setText(title)
                tabLayout.addTab(tab)
            }


            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    tab.let {
                        viewModel.getPopularCourse().observe(viewLifecycleOwner) { resources ->
                            when (resources.status) {
                                Status.LOADING -> {}
                                Status.SUCCESS -> {
                                    setupPopularCourse(resources.data)
                                }

                                Status.ERROR -> {
                                    toastMessage(requireContext(), resources.message.toString())
                                }
                            }

                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            })
        }

    }

    private fun displayCategory() {
        viewModel.getCategoryCourse().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    setupVisibilityProgressBar(binding.progressBar, true)
                }

                Status.SUCCESS -> {
                    setUpCategoryAdapter(resource.data)
                    setUpTabLayout(resource.data!!.data)
                    setupVisibilityProgressBar(binding.progressBar, false)
                }

                Status.ERROR -> {
                    setupVisibilityProgressBar(binding.progressBar, false)
                    toastMessage(requireContext(), " Error ${resource.message}")
                }
            }

        }
    }

    private fun setupPopularCourse(data: CourseResponse?) {
        val adapter = HomePopularCourseAdapter()
        adapter.submitList(data?.data)
        binding.recyclerViewPopularCourse.adapter = adapter
        binding.recyclerViewPopularCourse.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpCategoryAdapter(data: CategoryResponse?) {
        val adapter = HomeCategoryAdapter()
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.submitList(data?.data)
        binding.textViewSeeAllCategory.setOnClickListener {
            viewModel.showAllItem.observe(viewLifecycleOwner) { showALlItem ->
                adapter.showAllItems = showALlItem
                adapter.notifyDataSetChanged()
            }
            viewModel.toggleShowAllItem()
        }
    }
}