package com.am.finalproject.ui.course

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.adapter.course.TopicClassAdapter
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentCourseBinding
import com.am.finalproject.ui.account.AccountViewModel
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.bottom_sheet.FilterCourseBottomSheetFragment
import com.am.finalproject.ui.bottom_sheet.IsLoginRequiredBottomSheet
import com.am.finalproject.ui.bottom_sheet.OrdersBottomSheetFragment
import com.am.finalproject.ui.detail_payment.PaymentViewModel
import com.am.finalproject.ui.details.DetailsActivity
import com.am.finalproject.ui.search_result.SearchResultViewModel
import com.am.finalproject.utils.Destination
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class CourseFragment : Fragment() {
	private var _binding: FragmentCourseBinding? = null
	private val binding get() = _binding!!
	private val searchViewModel: SearchResultViewModel by inject()
	private val authViewModel: AuthViewModel by inject()
	private val paymentViewModel: PaymentViewModel by inject()
	private val accountViewModel: AccountViewModel by inject()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCourseBinding.inflate(inflater, container, false)
		displayViewTopicClass()
		navigation()
		setupFilterCourse()
		authViewModel.init(requireContext())
		setupSearch()
		return binding.root
	}

	private fun navigation() {
		binding.textViewFilter.setOnClickListener {
			val dataDummyLevel = "BEGINNER"
			FilterCourseBottomSheetFragment.show(childFragmentManager) { id, level ->
				searchViewModel.filter(id, level ?: dataDummyLevel)
			}
		}
	}

	private fun setupFilterCourse() {
		searchViewModel.filterCourse.observe(viewLifecycleOwner) { resources ->
			when (resources.status) {
				Status.LOADING -> {}
				Status.SUCCESS -> {
					val list = resources.data?.map { course ->
						val timeModule = course.module?.sumOf { it.time ?: 0 }
						val sizeModule = course.module?.size
						CourseEntity(
							course.title,
							course.id,
							course.image,
							course.level,
							course.authorBy,
							course.rating,
							course.price,
							course.category.title,
							timeModule ?: 0,
							sizeModule ?: 0,
							course.type
						)
					}
					setupDataTopicClassAdapter(list)
				}

				Status.ERROR -> {
					DisplayLayout.toastMessage(
						requireContext(),
						resources.message.toString(),
						false
					)
				}
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	private fun setupSearch() {
		binding.edtSearch.setOnTouchListener { _, event ->
			if (event.action == MotionEvent.ACTION_UP) {
				binding.edtSearch.clearFocus()
				Navigate.navigateToDestination(Destination.COURSE_TO_SEARCH, findNavController())
				return@setOnTouchListener true
			}
			return@setOnTouchListener false
		}
	}


	private fun displayViewTopicClass() {
		/*displays recyclerview topic class*/
		searchViewModel.getCourseLocalData().observe(viewLifecycleOwner) { resources ->
			when (resources.status) {
				Status.LOADING -> {
					DisplayLayout.setupVisibilityProgressBar(binding.progressBar, true)
				}

				Status.SUCCESS -> {
					DisplayLayout.setupVisibilityProgressBar(binding.progressBar, false)
					setupDataTopicClassAdapter(resources.data)
					setUpTabLayout(resources.data)
				}

				Status.ERROR -> {
					DisplayLayout.setupVisibilityProgressBar(binding.progressBar, false)
					DisplayLayout.toastMessage(
						requireContext(),
						resources.message.toString(),
						false
					)
				}
			}
		}
	}

	private fun setUpTabLayout(data: List<CourseEntity>?) {
		val tabLayout = binding.tabLayoutTopicClass
		tabLayout.removeAllTabs()
		tabLayout.addTab(tabLayout.newTab().setText("All"))
		val tabTitle = mutableSetOf<String>()
		data?.forEach {
			val type = it.type
			if (tabTitle.add(type)) {
				val tab = tabLayout.newTab().setText(type)
				tabLayout.addTab(tab)
			}
		}

		tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
			override fun onTabSelected(tab: TabLayout.Tab) {
				if (tab.text.toString() == "All") {
					getALlCourseLocalData()
				} else {
					searchViewModel.searchCourseByTypeLocalData(tab.text.toString())
						.observe(viewLifecycleOwner) { resource ->
							setupDataTopicClassAdapter(resource)
						}
				}
			}

			override fun onTabUnselected(tab: TabLayout.Tab) {}

			override fun onTabReselected(tab: TabLayout.Tab) {}
		})
	}

	private fun getALlCourseLocalData() {
		searchViewModel.readCourseAll().observe(viewLifecycleOwner) { data ->
			setupDataTopicClassAdapter(data)
		}
	}

	private fun setupDataTopicClassAdapter(data: List<CourseEntity>?) {
		val adapter = TopicClassAdapter()
		adapter.submitList(data)
		binding.recyclerViewTopicClass.layoutManager =
			LinearLayoutManager(requireContext())
		val token = authViewModel.getUser()?.accessToken.toString()
		adapter.onClick = { dataItemCourse ->
			if (authViewModel.isUserLogin()) {
				paymentViewModel.getHistoryOrderCourse(token)
					.observe(viewLifecycleOwner) { resources ->
						when (resources.status) {
							Status.LOADING -> {
								DisplayLayout.toastMessage(
									requireContext(),
									"Mohon tunggu sebentar",
									true
								)
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
											orderFreeCourse(dataItemCourse.id)
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
								DisplayLayout.toastMessage(
									requireContext(),
									resources.message.toString(),
									false
								)
							}
						}

					}
			} else {
				IsLoginRequiredBottomSheet.show(childFragmentManager)
			}
		}
		binding.recyclerViewTopicClass.adapter = adapter
	}

	private fun orderFreeCourse(courseId: String) {
		authViewModel.init(requireContext())
		val token = authViewModel.getUser()?.accessToken.toString()

		accountViewModel.getCurrentUser(token).observe(viewLifecycleOwner) { resources ->
			when (resources.status) {
				Status.LOADING -> {}
				Status.SUCCESS -> {
					val dataUserId = resources.data?.data?.id
					val status = "PROGRESS"
					paymentViewModel.postOrderFreeCourse(
						token,
						status,
						dataUserId.toString(),
						courseId
					).observe(viewLifecycleOwner) { result ->
						when (result.status) {
							Status.LOADING -> {}
							Status.SUCCESS -> {
								navigateToDetailActivity(courseId)
							}

							Status.ERROR -> {
								DisplayLayout.toastMessage(
									requireContext(),
									result.message.toString(),
									false
								)
							}
						}
					}
				}

				Status.ERROR -> {}
			}

		}
	}

	private fun navigateToDetailActivity(courseId: String?) {
		val intent = Intent(context, DetailsActivity::class.java).apply {
			putExtra(DetailsActivity.KEY_ID, courseId)
		}
		startActivity(intent)
	}

}