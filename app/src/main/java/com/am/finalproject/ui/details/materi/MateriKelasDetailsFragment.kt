package com.am.finalproject.ui.details.materi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.detail.StudyMaterialsAdapter
import com.am.finalproject.data.multiple_list.materials.DatabaseMaterials
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentMaterikelasdetailsBinding
import com.am.finalproject.ui.details.DetailsViewModel
import org.koin.android.ext.android.inject


class MateriKelasDetailsFragment : Fragment() {
    private var _binding: FragmentMaterikelasdetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by inject()
    private val args by lazy {
        arguments?.getString(KEY_ARGS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMaterikelasdetailsBinding.inflate(inflater, container, false)
        Log.e("SIMPLE_CHECK_ID", "$args")
        displayMaterials()
        return binding.root
    }

    private fun displayMaterials() {
        viewModel.getDetailByIdCourse(args.toString()).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    Log.e("SIMPLE_LOAD", "Load in Material Fragmnet")
                }

                Status.SUCCESS -> {
                    setupData(resource.data)
                    Log.e("SIMPLE_SUCCESS", "${resource.data}")
                }

                Status.ERROR -> {
                    Log.e("SIMPLE_LOAD", "${resource.message}")
                }
            }
        }
    }

    private fun setupData(data: List<DataItemCourse>?) {
        val adapter = StudyMaterialsAdapter()
        binding.recyclerViewStudyMaterials.adapter = adapter
        binding.recyclerViewStudyMaterials.layoutManager = LinearLayoutManager(requireContext())
        if (data != null) {
            val dataUpdate = DatabaseMaterials.getItem(data)
            adapter.updateList(dataUpdate)
        }
    }

    companion object {
        const val KEY_ARGS = "key_args"
        fun newInstance(id: String): MateriKelasDetailsFragment {
            val fragment = MateriKelasDetailsFragment()

            val args = Bundle()
            args.putString(KEY_ARGS, id)
            fragment.arguments = args

            return fragment
        }
    }

}