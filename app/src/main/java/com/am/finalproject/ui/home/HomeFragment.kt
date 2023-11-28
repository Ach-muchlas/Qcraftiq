package com.am.finalproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.am.finalproject.R
import com.am.finalproject.adapter.home.HomeCategoryAdapter
import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentHomeBinding
import com.am.finalproject.ui.tabLayout.popularCourse.PopularCourseFragment
import com.am.finalproject.ui.tabLayout.popularCourse.PopularCourseViewModel
import com.am.finalproject.utils.DisplayLayout.setUpVisibilityProgressBar
import com.am.finalproject.utils.DisplayLayout.toastMessage
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val popularCourseViewModel: PopularCourseViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        displayCategory()
        setUpTabLayout()
        return binding.root
    }

    private fun setUpTabLayout() {
        val tabLayout = binding.tabLayout
        val tabTitle = listOf("All", "Data Science", "UI/UX Design")
        for (title in tabTitle) {
            val tab = tabLayout.newTab().setText(title)
            tabLayout.addTab(tab)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.let {
                    val position = it.position
                    val title = tabTitle[position]
                    val fragment = PopularCourseFragment()
                    showFragment(fragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }

        })
        val firsFragment = PopularCourseFragment()
        showFragment(firsFragment)
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun displayCategory() {
        popularCourseViewModel.getCategory().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    setUpVisibilityProgressBar(binding.progressBar, true)
                }

                Status.SUCCESS -> {
                    setUpCategoryAdapter(resource.data)
                    setUpVisibilityProgressBar(binding.progressBar, false)
                    toastMessage(requireContext(), "${resource.message}")
                }

                Status.ERROR -> {
                    setUpVisibilityProgressBar(binding.progressBar, false)
                    toastMessage(requireContext(), " Error ${resource.message}")
                }
            }

        }
    }

    private fun setUpCategoryAdapter(data: CategoryResponse?) {
        val adapter = HomeCategoryAdapter()
        adapter.submitList(data?.data)
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager = GridLayoutManager(requireContext(), 2)
    }

}