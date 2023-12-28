package com.am.finalproject.ui.detail_payment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.am.finalproject.R
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentDetailPaymentBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.otp.OtpActivity
import com.am.finalproject.utils.DisplayLayout
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class DetailPaymentActivity : Fragment() {
	private lateinit var _binding: FragmentDetailPaymentBinding
	private val binding get() = _binding
	private val viewModel: AuthViewModel by inject()
	private lateinit var editTextMonthYear: EditText
	private val dateFormat = SimpleDateFormat("MM/yyyy", Locale.US)

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		// Inflate the layout for this fragment
		_binding = FragmentDetailPaymentBinding.inflate(inflater, container, false)
		setupEditText()
		showData(data)
		updatePayment()
//		val receiveBundle = intent.extras
//		val id = receiveBundle?.getString(OrdersBottomSheetFragment.KEY_ID)

		return binding.root
	}

	private fun setupEditText() {
		binding.edtCardNumber.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				binding.edtCardNumber.error = getString(R.string.warning_text_field_empty)
			}

			override fun afterTextChanged(s: Editable?) {
				val cardNumber = s.toString()

				if (cardNumber.length < 16) {
					binding.edtCardNumber.error = "Card number must be 16 digits"
				} else {
					// Clear the error if the length is valid
					binding.edtCardNumber.error = null
				}
			}
		})

		binding.edtCardHolder.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				binding.edtCardHolder.error = getString(R.string.warning_text_field_empty)
			}

			override fun afterTextChanged(s: Editable?) {

			}
		})

		binding.edtCardCvv.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				binding.edtCardCvv.error = getString(R.string.warning_text_field_empty)
			}

			override fun afterTextChanged(s: Editable?) {

			}
		})

		binding.edtExpire.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				binding.edtExpire.error = getString(R.string.warning_text_field_empty)
			}

			override fun afterTextChanged(s: Editable?) {
				validateDateFormat(s)
			}
		})

	}

	private fun validateDateFormat(text: Editable?) {
		try {
			// Attempt to parse the input as a date
			val date = dateFormat.parse(text.toString())

			// Check if the parsed date is not null
			if (date != null) {
				// Date is valid, you can use it if needed
			} else {
				// Date is not valid, handle the error (e.g., show an error message)
				editTextMonthYear.error = "Invalid date format"
			}
		} catch (e: ParseException) {
			// Exception occurred, handle the error (e.g., show an error message)
			editTextMonthYear.error = "Invalid date format"
		}
	}

	private fun updatePayment() {
		binding.apply {

			/*To Validate*/
			buttonPayment.setOnClickListener {
				val cardName = binding.edtCardHolder.text.toString()
				val cardNumber = binding.edtCardNumber.text.toString()
				val cardCvv = binding.edtCardCvv.text.toString()
				val expiryDate = binding.edtExpire.text.toString()

				viewModel.sendOrders(cardName,cardNumber,cardCvv,expiryDate
				)
					.observe(viewLifecycleOwner) { resources ->
						when (resources.status) {
							Status.LOADING -> {}
							Status.SUCCESS -> {
								val bundle = Bundle().apply {
									putString(KEY_CARDNAME, cardName)
									putString(KEY_CARDNUMBER, cardNumber)
									putString(KEY_CARDCVV, cardCvv)
									putString(KEY_EXPIRYDATE, expiryDate)
								}
								DisplayLayout.toastMessage(
									this@DetailPaymentActivity,
									resources.data?.message.toString(),
									true
								)
//								val intent =
//									Intent(this@DetailPaymentActivity OtpActivity ::class.java).apply {
//										putExtras(bundle)
//									}
//								startActivity(intent)
							}

							Status.ERROR -> {
								DisplayLayout.toastMessage(
									this@DetailPaymentActivity,
									resources.message.toString(),
									false
								)
							}
						}
					}
			}
		}
	}

	private fun showData(data: DataItemCourse) {
		Glide.with(this)
			.load(data.image)
			.fitCenter()
			.into(binding.imageViewCourse)
		val hargaAwal: Int = data.price
		val ppn: Double = data.price.times(0.11)
		val totalHarga: Int = hargaAwal.plus(ppn.toInt())

		binding.textViewTitleCourse.text = data.category.title
		binding.textViewPrice.text = formatCurrency(hargaAwal)
		binding.textViewTotal.text= formatCurrency(totalHarga)
		binding.textViewPpn.text = formatCurrency(ppn.toInt())
		binding.textViewDesc.text = data.title
		binding.textViewAuthor.text = data.authorBy

	}

	private fun formatCurrency(amount: Int?): String {
		if (amount == null) {
			return ""
		}
		val decimal = DecimalFormat("#,###")
		return "Rp. " + decimal.format(amount)
	}

	companion object {
		const val KEY_CARDNAME = "key_cardname"
		const val KEY_CARDNUMBER = "key_cardnumber"
		const val KEY_CARDCVV = "key_cardcvv"
		const val KEY_EXPIRYDATE = "key_expirydate"
	}

}