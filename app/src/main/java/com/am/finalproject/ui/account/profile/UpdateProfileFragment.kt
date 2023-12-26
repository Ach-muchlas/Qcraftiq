package com.am.finalproject.ui.account.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.am.finalproject.data.remote.LoginResult
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentUpdateProfileBinding
import com.am.finalproject.ui.account.AccountViewModel
import com.am.finalproject.utils.DisplayLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import org.koin.android.ext.android.inject

class UpdateProfileFragment : Fragment() {
    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private val arguments: UpdateProfileFragmentArgs by navArgs()
    private val viewModel: AccountViewModel by inject()
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        display()
        return binding.root
    }

    private fun display() {
        binding.imageViewPhotoProfile.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        viewModel.init(requireContext())
        val token = viewModel.getUser()?.accessToken
        binding.apply {
            val receiveArguments = arguments.objectParcelable
            edtName.setText(receiveArguments.name)
            edtEmail.setText(receiveArguments.email)
            edtPhone.setText(receiveArguments.phone)
            edtCountry.setText(receiveArguments.country)
            edtCity.setText(receiveArguments.city)

            val name = edtName.text
            val email = edtEmail.text
            val phone = edtPhone.text
            val country = edtCountry.text
            val city = edtCity.text

            binding.buttonSaveMyProfile.setOnClickListener {
                viewModel.updateUser(
                    token.toString(),
                    name.toString(),
                    email.toString(),
                    phone.toString(),
                    country.toString(),
                    city.toString(),
                    currentImageUri.toString()
                ).observe(viewLifecycleOwner) { resources ->
                    when (resources.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            viewModel.saveUser(LoginResult(resources.data?.data?.accessToken))
                            findNavController().popBackStack()
                        }

                        Status.ERROR -> {}
                    }
                }
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            DisplayLayout.toastMessage(requireContext(), "No media selected", false)
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Glide.with(requireContext()).load(it).transform(CircleCrop())
                .into(binding.imageViewPhotoProfile)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}