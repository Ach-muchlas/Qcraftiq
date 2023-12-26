package com.am.finalproject.ui.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.finalproject.R
import com.am.finalproject.adapter.course.TopicClassAdapter
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentCourseBinding
import com.am.finalproject.ui.bottom_sheet.FilterCourseBottomSheetFragment
import com.am.finalproject.ui.search_result.SearchResultViewModel
import com.am.finalproject.utils.Destination
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate
import com.google.android.material.tabs.TabLayout
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseFragment : Fragment() {
	private var _binding: FragmentCourseBinding? = null
	private val binding get() = _binding!!
	private val searchViewModel: SearchResultViewModel by inject()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCourseBinding.inflate(inflater, container, false)
		displayViewTopicClass()
		setupSearch()
		navigation()
		return binding.root
	}

	private fun navigation(){
		binding.textViewFilter.setOnClickListener {
			FilterCourseBottomSheetFragment.show(childFragmentManager)
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
		searchViewModel.getCourseAll().observe(viewLifecycleOwner) { resources ->
			when (resources.status) {
				Status.LOADING -> {}
				Status.SUCCESS -> {
					setupDataTopicClassAdapter(resources.data?.data)
					setUpTabLayout(data = resources.data?.data)
				}
				Status.ERROR -> {
					DisplayLayout.toastMessage(requireContext(), resources.message.toString(), false)
				}
			}
		}
	}

	private fun setUpTabLayout(data: List<DataItemCourse>?) {
		val tabLayout = binding.tabLayoutTopicClass
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
					searchViewModel.getCourseAll().observe(viewLifecycleOwner) { resources ->
						when (resources.status) {
							Status.LOADING -> {}
							Status.SUCCESS -> {
								setupDataTopicClassAdapter(resources.data?.data)
							}

							Status.ERROR -> {}
						}
					}
				} else {
					searchViewModel.filterByName(tab.text.toString())
						.observe(viewLifecycleOwner) { resource ->
							when (resource.status) {
								Status.LOADING -> {}
								Status.SUCCESS -> {
									setupDataTopicClassAdapter(resource.data)
								}

								Status.ERROR -> {}
							}

						}
				}
			}

			override fun onTabUnselected(tab: TabLayout.Tab) {}

			override fun onTabReselected(tab: TabLayout.Tab) {}
		})
	}

	override fun onPause() {
		super.onPause()
		Log.e("SIMPLE_PAUSE", "PAUSE")
		searchViewModel.filterCourse.observe(viewLifecycleOwner) { resources ->
			when (resources.status) {
				Status.LOADING -> {}
				Status.SUCCESS -> {
					val filteredData = resources.data
					setupDataTopicClassAdapter(filteredData)
					DisplayLayout.toastMessage(requireContext(), "data : $filteredData", true)
					Log.e("SIMPLE_FILTER_COURSE", "data Course :$filteredData")
				}

				Status.ERROR -> {
					StyleableToast.makeText(
						requireContext(),
						resources.message,
						Toast.LENGTH_SHORT,
						R.style.MyToast_IsRed
					).show()
				}
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		Log.e("SIMPLE_DESTROY", "DESTROY")
	}

	override fun onResume() {
		super.onResume()
		Log.e("SIMPLE_RESUME", "RESUME")
		searchViewModel.filterCourse.observe(viewLifecycleOwner) { resources ->
			Log.e("SIMPLE_FILTER_COURSE 1", "data Course :${resources.data}")
			when (resources.status) {
				Status.LOADING -> {}
				Status.SUCCESS -> {
					val filteredData = resources.data
					setupDataTopicClassAdapter(filteredData)
					DisplayLayout.toastMessage(requireContext(), "data : $filteredData", true)
					Log.e("SIMPLE_FILTER_COURSE", "data Course :$filteredData")
				}

				Status.ERROR -> {
					StyleableToast.makeText(
						requireContext(),
						resources.message,
						Toast.LENGTH_SHORT,
						R.style.MyToast_IsRed
					).show()
				}
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Log.e("SIMPLE_CREATE", "CREATE")
	}

	private fun setupDataTopicClassAdapter(data: List<DataItemCourse>?) {
		val adapter = TopicClassAdapter(childFragmentManager)
		adapter.submitList(data)
		binding.recyclerViewTopicClass.adapter = adapter
		binding.recyclerViewTopicClass.layoutManager = LinearLayoutManager(requireContext())
	}
}