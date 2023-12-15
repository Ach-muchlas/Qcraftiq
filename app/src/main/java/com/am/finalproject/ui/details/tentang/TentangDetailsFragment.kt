package com.am.finalproject.ui.details.tentang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.am.finalproject.R
import com.am.finalproject.databinding.FragmentTentangdetailsBinding
import com.am.finalproject.ui.details.DetailsViewModel
import org.koin.android.ext.android.inject


class TentangDetailsFragment : Fragment() {

    private var _binding : FragmentTentangdetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : DetailsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTentangdetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}