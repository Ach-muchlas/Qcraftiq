package com.am.finalproject.ui.bottomSheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.finalproject.R

class FilterCourseBottomSheetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_filter_course_bottom_sheet,
            container,
            false
        )
    }
}