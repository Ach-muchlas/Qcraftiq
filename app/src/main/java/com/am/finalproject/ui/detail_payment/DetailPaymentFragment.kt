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
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentDetailPaymentBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.otp.OtpActivity
import com.am.finalproject.ui.auth.register.RegisterActivity
import com.am.finalproject.utils.DisplayLayout
import org.koin.android.ext.android.inject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class DetailPaymentFragment : Fragment() {
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
		navigation()
		return binding.root
	}

	private fun setupEditText() {
		binding.edtCardNumber.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
				TODO("Not yet implemented")
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				TODO("Not yet implemented")
			}

			override fun afterTextChanged(s: Editable?) {
				TODO("Not yet implemented")
			}
		})

		binding.edtCardCvv.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
				TODO("Not yet implemented")
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				TODO("Not yet implemented")
			}

			override fun afterTextChanged(s: Editable?) {
				TODO("Not yet implemented")
			}
		})

		binding.edtExpire.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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

    private fun navigation() {
        binding.apply {

            /*To Validate*/
            buttonPayment.setOnClickListener {
                val cardHolder = binding.edtCardHolder.text.toString()
                val cardNumber = binding.edtCardNumber.text.toString()
                val cardCvv = binding.edtCardCvv.text.toString()
                val expiryDate = binding.edtExpire.text.toString()

                viewModel.register(cardHolder, cardNumber, cardCvv, expiryDate)
                    .observe(this@DetailPaymentFragment) { resources ->
                        when (resources.status) {
                            Status.LOADING -> {}
                            Status.SUCCESS -> {
                                val bundle = Bundle().apply {
                                    putString(RegisterActivity.KEY_NAME, cardHolder)
                                    putString(RegisterActivity.KEY_EMAIL, cardNumber)
                                    putString(RegisterActivity.KEY_PHONE, cardCvv)
                                    putString(RegisterActivity.KEY_PASSWORD, expiryDate)
                                }
                                DisplayLayout.toastMessage(
                                    this@DetailPaymentFragment,
                                    resources.data?.message.toString(),
                                    true
                                )
                                val intent =
                                    Intent(this@DetailPaymentFragment OtpActivity::class.java).apply {
                                        putExtras(bundle)
                                    }
                                startActivity(intent)
                            }

                            Status.ERROR -> {
                                DisplayLayout.toastMessage(
                                    this@DetailPaymentFragment,
                                    resources.message.toString(),
                                    false
                                )
                            }
                        }
                    }
            }
        }
    }

}