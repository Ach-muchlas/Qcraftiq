package com.am.finalproject.ui.detail_payment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityDetailPaymentBinding
import com.am.finalproject.ui.details.DetailsActivity
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPaymentBinding
    private var receiveDataBundle: DataItemCourse? = null
    private val calendar = Calendar.getInstance()
    private val viewModel: PaymentViewModel by inject()
    private var isContainerTransferBankVisible = false
    private var isContainerCreditCardVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
        visiblePaymentMethod()
        setupEditTextExpired()
        displayItemCourse()
        postOrder()
    }

    private fun initialize() {
        binding.containerTransferBank.root.visibility = View.GONE
        binding.containerCreditCard.root.visibility = View.GONE
        receiveDataBundle = intent.getParcelableExtra(KEY_BUNDLE)
        DisplayLayout.hideAppBar(this)
    }


    private fun navigation() {
        Log.e("SIMPLE", "Setting onClickListener in navigation")
        binding.buttonPayment.setOnClickListener {
//            DisplayLayout.toastMessage(this@DetailPaymentActivity, "COK", true)
            Log.e("SIMPLE", "CLICK2")
            postOrder()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupEditTextExpired() {
        binding.containerCreditCard.edtExpire.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                binding.containerCreditCard.edtExpire.clearFocus()
                showDatePickerDialog()
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Set the selected date to the Calendar
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update the EditText with the selected date
                Formatter.formatDate(binding.containerCreditCard.edtExpire, calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the date picker dialog
        datePickerDialog.show()
    }

    private fun displayItemCourse() {
        receiveDataBundle?.let { course ->
            binding.containerItemCourse.apply {
                val ppn = course.price * 0.11
                Glide.with(this@DetailPaymentActivity).load(course.image).into(imageViewCourse)
                textViewTaglineCourse.text = course.category.title
                textViewTitleCourse.text = course.title
                textViewAuthor.text = course.authorBy
                textViewPrice.text = Formatter.formatPrice(course.price)
                textViewPpn.text = Formatter.formatPPN(ppn.toInt())
                textViewTotal.text = Formatter.formatTotalPayment(course.price, ppn.toInt())
            }
        }
    }

    private fun postOrder() {
        viewModel.init(this)
        val token = viewModel.getUser()?.accessToken.toString()
        val cardNumber = binding.containerCreditCard.edtCardNumber.text
        val cardName = binding.containerCreditCard.edtCardHolder.text
        val cvv = binding.containerCreditCard.edtCardCvv.text
        val formattedDate = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        ).format(calendar.time)

        receiveDataBundle?.let { course ->
            val ppn = course.price * 0.11
            val total = course.price * ppn
            val courseId = course.id


            binding.buttonPayment.setOnClickListener {
                Log.e("SIMPLE", "CLICK")
                val response =
                    " expired : $formattedDate  cvv : ${cvv.toString()} + total : $total + card Name ${cardName.toString()} + number : ${cardNumber.toString()} + id : $courseId"
                Log.e("SIMPLE", "Data : $response")
                DisplayLayout.toastMessage(this, response, true)
                viewModel.postOrderCourse(
                    token,
                    formattedDate,
                    cvv.toString().toInt(),
                    total.toInt(),
                    cardName.toString(),
                    cardNumber.toString(),
                    courseId
                ).observe(this) { resources ->
                    when (resources.status) {
                        Status.LOADING -> {
                        }

                        Status.SUCCESS -> {
                            DisplayLayout.toastMessage(
                                this,
                                resources.data?.message.toString(),
                                true
                            )
                            Log.e("SIMPLE_SUCCESS", resources.data?.message.toString())
                            val intent = Intent(this, DetailsActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }

                        Status.ERROR -> {
                            DisplayLayout.toastMessage(this, resources.message.toString(), false)
                            Log.e("SIMPLE_ERROR", "error : ${resources.message}")
                        }
                    }
                }
            }

        }
    }

    private fun visiblePaymentMethod() {
        binding.apply {
            cardViewBankTransfer.setOnClickListener {
                isContainerTransferBankVisible = !isContainerTransferBankVisible
                binding.containerTransferBank.root.visibility =
                    if (isContainerTransferBankVisible) View.GONE else View.VISIBLE
            }
            cardViewCreditCard.setOnClickListener {
                isContainerCreditCardVisible = !isContainerCreditCardVisible
                containerCreditCard.root.visibility =
                    if (isContainerCreditCardVisible) View.GONE else View.VISIBLE
            }
        }
    }

    companion object {
        const val KEY_BUNDLE = "key_bundle"
    }
}