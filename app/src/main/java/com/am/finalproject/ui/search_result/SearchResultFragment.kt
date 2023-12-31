package com.am.finalproject.ui.search_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.course.TopicClassAdapter
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentSearchResultBinding
import com.am.finalproject.ui.bottom_sheet.OrdersBottomSheetFragment
import com.am.finalproject.ui.home.HomeFragment
import com.am.finalproject.utils.DisplayLayout
import org.koin.android.ext.android.inject

class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchResultViewModel by inject()
    private val category: String? by lazy { arguments?.getString(HomeFragment.KEY_CATEGORY_ID) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        navigation()
        initialize()
        DisplayLayout.setUpBottomNavigation(activity, true)
        displayCourse()
        return binding.root
    }

    private fun initialize() {
        if (category != null) {
            searchCategoryById()
        }
        search()
    }


    private fun navigation() {
        /*fungsi ini untuk kembali ke fragment sebelumnya*/
        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun searchCategoryById() {
        viewModel.searchCourseByCategory(category.toString())
            .observe(viewLifecycleOwner) { resources ->
                when (resources.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        if (resources.data?.isNotEmpty() == true){
                            setupDataAdapter(resources.data)
                        }
                    }

                    Status.ERROR -> {
                        DisplayLayout.toastMessage(
                            requireContext(),
                            resources.message.toString(),
                            false
                        )
                    }
                }
            }
    }

    private fun search() {
        binding.edtSearch.requestFocus()
        binding.edtSearch.doAfterTextChanged { text ->
            val query = text.toString()
            viewModel.searchCourseByName(query)
        }
    }

    private fun displayCourse() {
        viewModel.getCourseAll().observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupDataAdapter(resources.data?.data)
                }

                Status.ERROR -> {}
            }
        }
        viewModel.searchCourse.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupDataAdapter(resource.data)
                }

                Status.ERROR -> {}
            }
        }
    }

    private fun setupDataAdapter(data: List<DataItemCourse>?) {
        val adapter = TopicClassAdapter()
        adapter.submitList(data)
        binding.recyclerViewCourse.adapter = adapter
        binding.recyclerViewCourse.layoutManager = LinearLayoutManager(requireContext())

        adapter.callBackOpenOrdersBottomSheet = {
            OrdersBottomSheetFragment.show(childFragmentManager, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DisplayLayout.setUpBottomNavigation(activity, false )
        _binding = null
    }
}
