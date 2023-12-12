package com.am.finalproject.ui.account.payment_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.account.PaymentHistoryAdapter
import com.am.finalproject.data.remote.CourseResponse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentPaymentHistoryBinding
import com.am.finalproject.ui.search_result.SearchResultViewModel
import org.koin.android.ext.android.inject

class PaymentHistoryFragment : Fragment() {
    private var _binding: FragmentPaymentHistoryBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchResultViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentHistoryBinding.inflate(inflater, container, false)
        displayCourse()
        return binding.root
    }

    private fun displayCourse() {
        searchViewModel.getCourseAll().observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupPaymentHistoryAdapter(resources.data)
                }

                Status.ERROR -> {}
            }

        }
    }

    private fun setupPaymentHistoryAdapter(data: CourseResponse?) {
        val adapter = PaymentHistoryAdapter()
        adapter.submitList(data?.data)
        binding.recyclerPaymentHistory.adapter = adapter
        binding.recyclerPaymentHistory.layoutManager = LinearLayoutManager(requireContext())
    }

}