package com.am.finalproject.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.am.finalproject.adapter.home.HomeCategoryAdapter
import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.remote.DataItemCategory
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentHomeBinding
import com.am.finalproject.ui.tabLayout.popularCourse.PopularCourseFragment
import com.am.finalproject.utils.DisplayLayout.setupVisibilityProgressBar
import com.am.finalproject.utils.DisplayLayout.showFragment
import com.am.finalproject.utils.DisplayLayout.toastMessage
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        displayCategory()
        return binding.root
    }

    private fun setUpTabLayout(data: List<DataItemCategory?>?) {
        val tabLayout = binding.tabLayout
        data?.forEach {
            val tabTitle = listOf(it?.title)
            Log.e("CHECK", "title : $tabTitle")

            for (title in tabTitle) {
                val tab = tabLayout.newTab().setText(title)
                tabLayout.addTab(tab)
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    tab.let {
                        val fragment = PopularCourseFragment()
                        showFragment(fragment, childFragmentManager)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }

            })
            val firsFragment = PopularCourseFragment()
            showFragment(firsFragment, childFragmentManager)
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