package com.am.finalproject.ui.account.payment_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.account.PaymentHistoryAdapter
import com.am.finalproject.data.remote.TrackingClassResponse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentPaymentHistoryBinding
import com.am.finalproject.ui.account.AccountViewModel
import com.am.finalproject.utils.DisplayLayout
import org.koin.android.ext.android.inject

class PaymentHistoryFragment : Fragment() {
    private var _binding: FragmentPaymentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentHistoryBinding.inflate(inflater, container, false)
        displayCourse()
        navigation()
        DisplayLayout.setUpBottomNavigation(activity, true)
        return binding.root
    }

    private fun navigation() {
        binding.imageViewButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayCourse() {
        viewModel.init(requireContext())
        val token = viewModel.getUser()?.accessToken
        viewModel.getPaymentHistory(token.toString()).observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupPaymentHistoryAdapter(resources.data)
                }

                Status.ERROR -> {}
            }

        }
    }

    private fun setupPaymentHistoryAdapter(data: TrackingClassResponse?) {
        val adapter = PaymentHistoryAdapter()
        adapter.submitList(data?.data)
        binding.recyclerPaymentHistory.adapter = adapter
        binding.recyclerPaymentHistory.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        DisplayLayout.setUpBottomNavigation(activity, false)
    }

}