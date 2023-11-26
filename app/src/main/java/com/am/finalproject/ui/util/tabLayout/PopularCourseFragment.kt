package com.am.finalproject.ui.util.tabLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.home.HomePopularCourseAdapter
import com.am.finalproject.data.dataDummyCourse
import com.am.finalproject.databinding.FragmentPopularCourseBinding

class PopularCourseFragment : Fragment() {
    private var _binding: FragmentPopularCourseBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularCourseBinding.inflate(inflater, container, false)
        val tabTitle = arguments?.getString(TAB_TITLE)
        val title = binding.textView
        title.text = tabTitle
        setUpPopularCourseAdapter()
        return binding.root
    }

    private fun setUpPopularCourseAdapter() {
        val adapter = HomePopularCourseAdapter()
        adapter.submitList(dataDummyCourse)
        binding.recyclerViewPopularCourse.adapter = adapter
        binding.recyclerViewPopularCourse.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {
        private const val TAB_TITLE = "tab_title"
        fun newsInstance(tabTitle: String): PopularCourseFragment {
            val fragment = PopularCourseFragment()
            val arg = Bundle()
            arg.putString(TAB_TITLE, tabTitle)
            fragment.arguments = arg
            return fragment
        }
    }

}