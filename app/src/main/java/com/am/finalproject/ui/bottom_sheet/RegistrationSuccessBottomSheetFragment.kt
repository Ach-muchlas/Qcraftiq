package com.am.finalproject.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.finalproject.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class RegistrationSuccessBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_success_bottom_sheet, container, false)
    }

}