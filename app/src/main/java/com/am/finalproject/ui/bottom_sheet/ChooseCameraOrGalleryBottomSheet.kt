package com.am.finalproject.ui.bottom_sheet

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.am.finalproject.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseCameraOrGalleryBottomSheet : BottomSheetDialogFragment() {

    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_choose_camera_or_galery_bottom_sheet,
            container,
            false
        )
    }



}