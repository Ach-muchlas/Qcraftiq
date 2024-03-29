package com.am.finalproject.ui.details.tentang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.am.finalproject.R
import com.am.finalproject.databinding.FragmentTentangdetailsBinding
import com.am.finalproject.ui.bottom_sheet.OrdersBottomSheetFragment
import com.am.finalproject.ui.details.DetailsViewModel
import com.am.finalproject.ui.details.materi.MateriKelasDetailsFragment
import com.am.finalproject.utils.DisplayLayout
import org.koin.android.ext.android.inject


class TentangDetailsFragment : Fragment() {

    private var _binding : FragmentTentangdetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : DetailsViewModel by inject()

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