package com.am.finalproject.ui.searchResult

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.course.TopicClassAdapter
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentSearchResultBinding
import org.koin.android.ext.android.inject

class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchResultViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        navigation()
        search()
        displayCourse()
        return binding.root
    }

    private fun navigation(){
        /*fungsi ini untuk kembali ke fragment sebelumnya*/
        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
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
    }

}
