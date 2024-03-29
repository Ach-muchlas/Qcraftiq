package com.am.finalproject.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.finalproject.databinding.FragmentAccountBinding
import com.am.finalproject.ui.auth.login.LoginActivity
import com.am.finalproject.utils.Destination
import com.am.finalproject.utils.Navigate
import org.koin.android.ext.android.inject

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        navigation()
        return binding.root
    }

    private fun navigation() {
        binding.apply {
            /*To Profile user*/
            textViewMyProfile.setOnClickListener {
                Navigate.navigateToDestination(Destination.ACCOUNT_TO_PROFILE, findNavController())
            }

            /*To History Payment User*/
            textViewPaymentHistory.setOnClickListener {
                Navigate.navigateToDestination(
                    Destination.ACCOUNT_TO_HISTORY_PAYMENT,
                    findNavController()
                )
            }

            /*To Change Password User*/
            textViewChangePassword.setOnClickListener {
                Navigate.navigateToDestination(
                    Destination.ACCOUNT_TO_CHANGE_PASSWORD,
                    findNavController()
                )
            }

            /*Log Out user*/
            textViewLogout.setOnClickListener {
                viewModel.init(requireContext())
                viewModel.logout()
                Navigate.intentActivity(requireContext(), LoginActivity::class.java)
            }

        }
    }


}