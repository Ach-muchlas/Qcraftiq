package com.am.finalproject.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.filter.FilterAdapter
import com.am.finalproject.data.multiple_list.filter.DataItemFilter
import com.am.finalproject.data.multiple_list.filter.DatabaseFilter
import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentFilterCourseBottomSheetBinding
import com.am.finalproject.ui.search_result.SearchResultViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class FilterCourseBottomSheetFragment(private val callBackFilter: (String) -> Unit) :
    BottomSheetDialogFragment() {
    private var _binding: FragmentFilterCourseBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchResultViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterCourseBottomSheetBinding.inflate(inflater, container, false)
        displayFilter()
        navigation()
        return binding.root
    }

    private fun navigation() {
        /*close the bottom sheet*/
        binding.imageViewCloseButton.setOnClickListener {
            dismiss()
        }
    }

    private fun displayFilter() {
        viewModel.getCategoryAll().observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupCategoryAdapter(resources.data)
                }

                Status.ERROR -> {}
            }
        }
    }

    private fun setupCategoryAdapter(data: CategoryResponse?) {
        val adapter = FilterAdapter()
        binding.recyclerViewFilter.adapter = adapter
        binding.recyclerViewFilter.layoutManager =
            LinearLayoutManager(requireContext())
        if (data != null) {
            val dataUpdate = DatabaseFilter.getItem(data)
            adapter.updateList(dataUpdate)
        }

        binding.buttonFilter.setOnClickListener {
            val checkedItem =
                adapter.itemList.filter { it is DataItemFilter.Item && it.isChecked } as List<DataItemFilter.Item>
            val categoryId = checkedItem.filter { it.type == "Category" }.map { it.id }
            callBackFilter.invoke(categoryId.joinToString())

            dismissNow()
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager, callBackDataFilter: (String) -> Unit) {
            val bottomSheetFilter = FilterCourseBottomSheetFragment(callBackDataFilter)
            bottomSheetFilter.show(fragmentManager, bottomSheetFilter.tag)
        }
    }
}