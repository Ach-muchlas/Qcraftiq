package com.am.finalproject.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.finalproject.databinding.FragmentAccountBinding
import com.am.finalproject.ui.auth.login.LoginFragment

class AccountFragment : Fragment() {

	private lateinit var binding: FragmentAccountBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentAccountBinding.inflate(inflater, container, false)
		navigation()

		return binding.root
	}

	private fun navigation() {
		binding.imageViewEditProfile.setOnClickListener {
			val intent = Intent(requireContext(), MyProfileFragment::class.java)
			startActivity(intent)
		}
		binding.textViewMyProfile.setOnClickListener {
			val intent = Intent(requireContext(), MyProfileFragment::class.java)
			startActivity(intent)
		}

		binding.imageViewChangePassword.setOnClickListener {
			val intent = Intent(requireContext(), ChangePasswordFragment::class.java)
			startActivity(intent)
		}
		binding.textViewChangePassword.setOnClickListener {
			val intent = Intent(requireContext(), ChangePasswordFragment::class.java)
			startActivity(intent)
		}

		binding.imageViewPaymentHistory.setOnClickListener {
			val intent = Intent(requireContext(), PaymentHistoryFragment::class.java)
			startActivity(intent)
		}
		binding.textViewPaymentHistory.setOnClickListener {
			val intent = Intent(requireContext(), PaymentHistoryFragment::class.java)
			startActivity(intent)
		}

		binding.imageViewLogout.setOnClickListener {
			val intent = Intent(requireContext(), LoginFragment::class.java)
			startActivity(intent)
		}
		binding.textViewLogout.setOnClickListener {
			val intent = Intent(requireContext(), LoginFragment::class.java)
			startActivity(intent)
		}
	}
}