package com.am.finalproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.am.finalproject.R
import com.am.finalproject.adapter.home.HomeCategoryAdapter
import com.am.finalproject.data.dataDummyCategory
import com.am.finalproject.databinding.FragmentHomeBinding
import com.am.finalproject.ui.util.tabLayout.PopularCourseFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpCategoryAdapter()
        setUpTabLayout()
//        setUpPopularCourseAdapter()
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
                    val fragment = PopularCourseFragment.newsInstance(title)
                    showFragment(fragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }

        })
        val firstTabTitle = tabTitle.first()
        val firsFragment = PopularCourseFragment.newsInstance(firstTabTitle)
        showFragment(firsFragment)
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun setUpCategoryAdapter() {
        val adapter = HomeCategoryAdapter()
        adapter.submitList(dataDummyCategory)
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager = GridLayoutManager(requireContext(), 2)
    }

}