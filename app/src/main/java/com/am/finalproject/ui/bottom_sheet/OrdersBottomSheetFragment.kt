package com.am.finalproject.ui.bottom_sheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.databinding.FragmentOrderBottomSheetBinding
import com.am.finalproject.ui.detail_payment.DetailPaymentActivity
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OrdersBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentOrderBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var receiveCourse: CourseEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBottomSheetBinding.inflate(inflater, container, false)
        displays()
        navigation()
        return binding.root
    }

    private fun displays() {
        receiveCourse  = arguments?.getParcelable(KEY_ID)

        receiveCourse?.let { course ->
            binding.containerItemCourse.apply {
                Glide.with(requireContext()).load(course.image).into(imageContent)
                textViewTagLineCategory.text = course.categoryTitle
                textViewRate.text = course.rating.toString()
                textViewTitleCourse.text = course.title
                textViewRate.text = course.rating.toString()
                textViewMentor.text = course.authorBy
                textViewLevelCourse.text = course.level
                textViewContentCard.text = Formatter.formatCurrency(course.price)
                iconContentCard.visibility = View.GONE
                textViewModule.text = Formatter.formatSizeModule(course.module)
                textViewTime.text = Formatter.formatTimeSecondToMinute(course.time)
                }
        }
    }

    private fun navigation(){
        binding.buttonBuy.setOnClickListener {
            val parsingData = Bundle().apply {
                putParcelable(DetailPaymentActivity.KEY_BUNDLE, receiveCourse)
            }
            val intent = Intent(requireContext(), DetailPaymentActivity::class.java).apply {
                putExtras(parsingData)
            }
            startActivity(intent)
            dismiss()
        }

        binding.imageViewCloseButton.setOnClickListener {
            dismiss()
        }

    }
    companion object {
        const val KEY_ID = "key_id"
        fun show(fragmentManager: FragmentManager, data: CourseEntity) {
            val bottomSheetFilter = OrdersBottomSheetFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_ID, data)
            bottomSheetFilter.arguments = bundle
            bottomSheetFilter.show(fragmentManager, bottomSheetFilter.tag)
        }
    }

}