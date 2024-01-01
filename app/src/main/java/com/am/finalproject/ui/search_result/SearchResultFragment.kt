package com.am.finalproject.ui.search_result

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.course.TopicClassAdapter
import com.am.finalproject.data.local.entity.CourseEntity
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
    private val category: String? by lazy { arguments?.getString(HomeFragment.KEY_CATEGORY_TITle) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        navigation()
        initialize()
        search()
        DisplayLayout.setUpBottomNavigation(activity, true)
        return binding.root
    }

    private fun initialize() {
        if (category != null) {
            searchCategoryById()
        } else {
            displayCourse()
            displaySearchCourse()
        }
    }


    private fun navigation() {
        /*fungsi ini untuk kembali ke fragment sebelumnya*/
        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun searchCategoryById() {
        binding.edtSearch.visibility = View.GONE
        viewModel.searchByNameLocalData(category.toString())
            .observe(viewLifecycleOwner) { result ->
                setupDataAdapter(result)
            }
    }

    private fun search() {
        binding.edtSearch.requestFocus()
        if (binding.edtSearch.requestFocus()){
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        }

        binding.edtSearch.doAfterTextChanged { text ->
            val query = text.toString()
            viewModel.searchCourseByName(query)
        }
    }

    private fun displayCourse() {
        viewModel.readCourseAll().observe(viewLifecycleOwner) { resources ->
            setupDataAdapter(resources)
        }
    }

    private fun displaySearchCourse(){
        viewModel.searchCourse.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    val list = resource.data?.map { course ->
                        val timeModule = course.module?.sumOf { it.time ?: 0 }
                        val sizeModule = course.module?.size
                        CourseEntity(
                            course.title,
                            course.id,
                            course.image,
                            course.level,
                            course.authorBy,
                            course.rating,
                            course.price,
                            course.category.title,
                            timeModule ?: 0,
                            sizeModule ?: 0,
                            course.type
                        )
                    }
                    setupDataAdapter(list)
                }

                Status.ERROR -> {}
            }
        }
    }

    private fun setupDataAdapter(data: List<CourseEntity>?) {
        val adapter = TopicClassAdapter()
        adapter.submitList(data)
        binding.recyclerViewCourse.adapter = adapter
        binding.recyclerViewCourse.layoutManager = LinearLayoutManager(requireContext())
//        adapter.callBackOpenOrdersBottomSheet = {
//            OrdersBottomSheetFragment.show(childFragmentManager, it)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DisplayLayout.setUpBottomNavigation(activity, false )
        binding.edtSearch.visibility = View.VISIBLE
        _binding = null
    }
}
