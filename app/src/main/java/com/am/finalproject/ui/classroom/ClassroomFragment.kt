package com.am.finalproject.ui.classroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.finalproject.R
import com.am.finalproject.databinding.FragmentClassroomBinding
import com.am.finalproject.ui.tabLayout.popularCourse.PopularCourseFragment
import com.am.finalproject.ui.tabLayout.progressCourse.ProgressCourseFragment
import com.google.android.material.tabs.TabLayout

class ClassroomFragment : Fragment() {
    private var _binding: FragmentClassroomBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassroomBinding.inflate(inflater, container, false)
        setUpTabLayout()
        return binding.root
    }

    private fun setUpTabLayout() {
        val tabLayout = binding.tabLayoutClass
        val tabTitle = listOf("All", "In Progress", "Done")
        for (title in tabTitle) {
            val tab = tabLayout.newTab().setText(title)
            tabLayout.addTab(tab)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.let {
                    val fragment = ProgressCourseFragment()
                    showFragment(fragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }

        })
        val firsFragment = ProgressCourseFragment()
        showFragment(firsFragment)
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}