package com.am.finalproject.ui.account.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.finalproject.data.remote.DataUser
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentMyProfileBinding
import com.am.finalproject.ui.account.AccountViewModel
import com.am.finalproject.utils.DisplayLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import org.koin.android.ext.android.inject

class MyProfileFragment : Fragment() {

	private var _binding: FragmentMyProfileBinding? = null
	private val binding get() = _binding!!
	private val viewModel: AccountViewModel by inject()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		displayProfileUser()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentMyProfileBinding.inflate(inflater, container, false)
		displayProfileUser()
		navigation()
		DisplayLayout.setUpBottomNavigation(activity, true)
		return binding.root
	}

	private fun displayProfileUser() {
		viewModel.init(requireContext())
		val token = viewModel.getUser()?.accessToken
		viewModel.getCurrentUser(token.toString()).observe(viewLifecycleOwner) { resources ->
			when (resources.status) {
				Status.LOADING -> {}
				Status.SUCCESS -> setupDataProfile(resources.data?.data)
				Status.ERROR -> DisplayLayout.toastMessage(
					requireContext(),
					resources.message.toString(),
					false
				)
			}
		}
	}

	private fun setupDataProfile(data: DataUser?) {
		binding.apply {
			Glide.with(root.context).load(data?.image).transform(CircleCrop())
				.into(imageViewPhotoProfile)
			textViewValueName.text = data?.name
			textViewValueEmail.text = data?.email
			textViewValuePhone.text = data?.phone
			textViewValueCountry.text = data?.country
			textViewValueCity.text = data?.city

			binding.buttonEditMyProfile.setOnClickListener {
				val action =
					MyProfileFragmentDirections.actionMyProfileFragmentToUpdateProfileFragment(data!!)
				findNavController().navigate(action)
			}
		}
	}

	private fun navigation() {
		binding.apply {
			imageViewButtonBack.setOnClickListener {
				findNavController().popBackStack()
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		DisplayLayout.setUpBottomNavigation(activity, false)
		_binding = null
	}
}