package com.am.finalproject.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.R
import com.am.finalproject.adapter.home.HomeCategoryAdapter
import com.am.finalproject.adapter.home.HomePopularCourseAdapter
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentHomeBinding
import com.am.finalproject.ui.search_result.SearchResultViewModel
import com.am.finalproject.utils.Destination
import com.am.finalproject.utils.DisplayLayout.setupVisibilityProgressBar
import com.am.finalproject.utils.DisplayLayout.toastMessage
import com.am.finalproject.utils.Navigate
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private val searchViewModel: SearchResultViewModel by inject()
    private lateinit var sharedpref: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        displayCategory()
        displayPopularCourse()
        setupSearch()
        sharedpref = UserPreferences(requireContext())
        binding.textViewSeeAllPopularCourse.setOnClickListener {
            sharedpref.clearUser()
        }
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSearch() {
        binding.edtSearch.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                binding.edtSearch.clearFocus()
                Navigate.navigateToDestination(Destination.HOME_TO_SEARCH, findNavController())
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

    /*This function is to display tabs in the popular course.*/
    private fun setUpTabLayout(data: List<CategoryEntity?>?) {
        val tabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.all)))
        val tabTitle = mutableSetOf<String>()
        data?.forEach {
            val title = it?.title
            if (tabTitle.add(title.toString())) {
                val tab = tabLayout.newTab().setText(title)
                tabLayout.addTab(tab)
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text.toString() == "All") {
                    getALlCourseLocalData()
                }else {
                    searchViewModel.searchByNameLocalData(tab.text.toString())
                        .observe(viewLifecycleOwner) { data ->
                            setupPopularCourseAdapter(data)
                        }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


    /*Function to retrieve popular course data from the local database.*/
    private fun getALlCourseLocalData() {
        viewModel.readCourseAll().observe(viewLifecycleOwner) { data ->
            setupPopularCourseAdapter(data)
        }
    }

    /*Function to display popular courses.*/
    /*This function has implemented offline first.*/
    private fun displayPopularCourse() {
        viewModel.getCourseLocalData()
            .observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result.status) {
                        Status.LOADING -> {
                            setupVisibilityProgressBar(binding.progressBarPopularCourse, true)
                        }

                        Status.SUCCESS -> {
                            setupVisibilityProgressBar(binding.progressBarPopularCourse, false)
                            setupPopularCourseAdapter(data = result.data!!)
                        }

                        Status.ERROR -> {
                            setupVisibilityProgressBar(binding.progressBarPopularCourse, false)
                            toastMessage(requireContext(), result.message.toString(), false)
                        }
                    }
                }
            }
    }

    /*This function is used to display categories.*/
    private fun displayCategory() {
        viewModel.getCategoryLocalData().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    setupVisibilityProgressBar(binding.progressBar, true)
                }

                Status.SUCCESS -> {
                    setUpCategoryAdapter(resource.data!!)
                    setUpTabLayout(resource.data)
                    setupVisibilityProgressBar(binding.progressBar, false)
                }

                Status.ERROR -> {
                    setupVisibilityProgressBar(binding.progressBar, false)
                    toastMessage(requireContext(), " Error ${resource.message}", false)
                }
            }

        }
    }

    /*The function is used to set up a data adapter for popular courses.*/
    private fun setupPopularCourseAdapter(data: List<CourseEntity>) {
        val adapter = HomePopularCourseAdapter()
        adapter.submitList(data)
        binding.recyclerViewPopularCourse.adapter = adapter
        binding.recyclerViewPopularCourse.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    /*The function is used to set up a data adapter for popular courses. */
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpCategoryAdapter(data: List<CategoryEntity>) {
        val adapter = HomeCategoryAdapter()
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.submitList(data)
        binding.textViewSeeAllCategory.setOnClickListener {
            viewModel.showAllItem.observe(viewLifecycleOwner) { showALlItem ->
                adapter.showAllItems = showALlItem
                adapter.notifyDataSetChanged()
            }
            viewModel.toggleShowAllItem()
        }
    }
}