package com.am.finalproject.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.R
import com.am.finalproject.adapter.home.HomeCategoryAdapter
import com.am.finalproject.adapter.home.HomePopularCourseAdapter
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentHomeBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.bottom_sheet.IsLoginRequiredBottomSheet
import com.am.finalproject.ui.bottom_sheet.OrdersBottomSheetFragment
import com.am.finalproject.ui.detail_payment.PaymentViewModel
import com.am.finalproject.ui.details.DetailsActivity
import com.am.finalproject.ui.search_result.SearchResultViewModel
import com.am.finalproject.utils.Destination
import com.am.finalproject.utils.DisplayLayout.setupVisibilityProgressBar
import com.am.finalproject.utils.DisplayLayout.toastMessage
import com.am.finalproject.utils.Navigate
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private val searchViewModel: SearchResultViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val paymentViewModel: PaymentViewModel by inject()
    private val calender = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        authViewModel.init(requireContext())
        displayCategory()
        displayPopularCourse()
        setupSearch()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSearch() {
        binding.edtSearch.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                binding.edtSearch.clearFocus()
                Navigate.navigateToDestination(Destination.HOME_TO_SEARCH, findNavController())
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

    /*This function is to display tabs in the popular course.*/
    private fun setUpTabLayout(data: List<CategoryEntity>?) {
        val tabLayout = binding.tabLayout
        tabLayout.removeAllTabs()
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.all)))

        val tabTitle = mutableSetOf<String>()
        data?.forEach {
            val title = it.title
            if (tabTitle.add(title)) {
                val tab = tabLayout.newTab().setText(title)
                tabLayout.addTab(tab)

            }
        }


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text.toString() == "All") {
                    displayPopularCourse()
                } else {
                    getCourseByCategoryTitle(tab.text.toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


    /*Function to retrieve popular course data from the local database.*/
    private fun getCourseByCategoryTitle(categoryTitle: String) {
        searchViewModel.searchCourseByCategory(categoryTitle)
            .observe(viewLifecycleOwner) { resources ->
                when (resources.status) {
                    Status.LOADING -> {
                        setupVisibilityProgressBar(binding.progressBarPopularCourse, true)
                    }
                    Status.SUCCESS -> {
                        setupVisibilityProgressBar(binding.progressBarPopularCourse, false)
                        setupPopularCourseAdapter(resources.data)
                    }

                    Status.ERROR -> {
                        toastMessage(requireContext(), resources.message.toString(), false)
                    }
                }
            }
    }

    /*Function to display popular courses.*/
    /*This function has implemented offline first.*/
    private fun displayPopularCourse() {
        viewModel.getCourse()
            .observe(viewLifecycleOwner) { resources ->
                when (resources.status) {
                    Status.LOADING -> {
                        setupVisibilityProgressBar(binding.progressBarPopularCourse, true)
                    }

                    Status.SUCCESS -> {
                        setupVisibilityProgressBar(binding.progressBarPopularCourse, false)
                        setupPopularCourseAdapter(data = resources.data?.data)
                    }

                    Status.ERROR -> {
                        setupVisibilityProgressBar(binding.progressBarPopularCourse, false)
                        toastMessage(requireContext(), resources.message.toString(), false)
                    }
                }
            }
    }

    /*This function is used to display categories.*/
    private fun displayCategory() {
        viewModel.getCategoryLocalData().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    setupVisibilityProgressBar(binding.progressBar, true)
                }

                Status.SUCCESS -> {
                    setUpCategoryAdapter(resource.data)
                    setupVisibilityProgressBar(binding.progressBar, false)
                    setUpTabLayout(resource.data)
                }

                Status.ERROR -> {
                    setupVisibilityProgressBar(binding.progressBar, false)
                    toastMessage(requireContext(), " Error ${resource.message}", false)
                    Log.e("SIMPLE", "${resource.message}")
                }
            }

        }
    }

    private fun orderCourse(dataItem: DataItemCourse) {
        val token = authViewModel.getUser()?.accessToken
        val formattedDate = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        ).format(calender.time)
        val ppn = dataItem.price * 0.11
        val total = dataItem.price * ppn

        paymentViewModel.postOrderCourse(
            token.toString(),
            formattedDate,
            0,
            total.toInt(),
            "",
            "",
            dataItem.id
        ).observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {
                    toastMessage(requireContext(), "Mohon tunggu sebentar", true)
                }

                Status.ERROR -> {
                    toastMessage(requireContext(), resources.message.toString(), false)
                }

                Status.SUCCESS -> {
                    toastMessage(
                        requireContext(),
                        resources.data?.message.toString(),
                        true
                    )
                    val bundle = Bundle().apply {
                        putString(DetailsActivity.KEY_ID, dataItem.id)
                    }
                    val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
                        putExtras(bundle)
                    }
                    startActivity(intent)
                }
            }

        }
    }

    /*The function is used to set up a data adapter for popular courses.*/
    private fun setupPopularCourseAdapter(data: List<DataItemCourse>?) {
        val adapter = HomePopularCourseAdapter()
        adapter.submitList(data)
        binding.recyclerViewPopularCourse.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val token = authViewModel.getUser()?.accessToken.toString()
        adapter.onClick = { dataItemCourse ->
            if (authViewModel.isUserLogin()) {
                paymentViewModel.getHistoryOrderCourse(token)
                    .observe(viewLifecycleOwner) { resources ->
                        when (resources.status) {
                            Status.LOADING -> {
                                toastMessage(requireContext(), "Mohon tunggu sebentar", true)
                            }

                            Status.SUCCESS -> {
                                val courseAlreadyPurchased =
                                    resources.data?.data?.any {
                                        it?.course?.id == dataItemCourse.id && it.status in listOf(
                                            "WAITING",
                                            "APPROVED"
                                        )
                                    }
                                when (dataItemCourse.type) {
                                    "FREE" -> {
                                        if (courseAlreadyPurchased == true) {
                                            navigateToDetailActivity(dataItemCourse.id)
                                        } else {
                                            orderCourse(dataItemCourse)
                                        }
                                    }

                                    "PREMIUM" -> {
                                        if (courseAlreadyPurchased == true) {
                                            navigateToDetailActivity(dataItemCourse.id)
                                        } else {
                                            OrdersBottomSheetFragment.show(
                                                childFragmentManager,
                                                dataItemCourse
                                            )
                                        }
                                    }
                                }
                            }

                            Status.ERROR -> {
                                toastMessage(requireContext(), resources.message.toString(), false)
                            }
                        }

                    }
            } else {
                IsLoginRequiredBottomSheet.show(childFragmentManager)
            }
        }
        binding.recyclerViewPopularCourse.adapter = adapter
    }


    /*The function is used to set up a data adapter for popular courses. */
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpCategoryAdapter(data: List<CategoryEntity>?) {
        val adapter = HomeCategoryAdapter()
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.submitList(data)
        binding.textViewSeeAllCategory.setOnClickListener {
            viewModel.showAllItem.observe(viewLifecycleOwner) { showALlItem ->
                adapter.showAllItems = showALlItem
                adapter.notifyDataSetChanged()
            }
            viewModel.toggleShowAllItem()
        }

        adapter.callBackSearchByIdCategory = { categoryId ->
            val bundle = Bundle().apply {
                putString(KEY_CATEGORY_ID, categoryId)
            }
            findNavController().navigate(
                R.id.action_navigation_home_to_searchResultFragment,
                bundle
            )
        }
    }

    private fun navigateToDetailActivity(courseId: String?) {
        val intent = Intent(context, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.KEY_ID, courseId)
        }
        startActivity(intent)
    }


    companion object {
        const val KEY_CATEGORY_ID = "key_category_title"
    }
}