package uz.mnsh.qazo.presentation.welcome.data_collect_screen.page

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import it.sephiroth.android.library.numberpicker.NumberPicker
import uz.mnsh.qazo.R
import uz.mnsh.qazo.common.Constants
import uz.mnsh.qazo.common.Gender
import uz.mnsh.qazo.databinding.PageDateBinding
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.DataCollectViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DatePage : Fragment(), DatePickerDialog.OnDateSetListener {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private var _binding: PageDateBinding? = null
    private val binding get() = _binding!!
    private val parentViewModel: DataCollectViewModel by viewModels({ requireParentFragment() })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageDateBinding.inflate(layoutInflater, container, false)

        setDatePage()

        return binding.root
    }

    private fun setDatePage() {
        setUpFemaleWindow()
        setUpCalendarUI()
        setUpPubertyAgeNP()
    }


    private fun setUpFemaleWindow() {
        parentViewModel.gender.observe(viewLifecycleOwner) { gender ->
            when (gender) {
                is Gender.MALE -> {
                    parentViewModel.setPubertyAge(Constants.DEFAULT_PUBERTY_AGE_MALE)
                    parentViewModel.setMenstrualDays(0)
                    binding.spacer.visibility = View.VISIBLE
                    binding.femaleTv.visibility = View.GONE
                    binding.femaleCard.visibility = View.GONE
                }
                is Gender.FEMALE -> {
                    val menstrualDays = binding.femaleNp.progress
                    parentViewModel.setPubertyAge(Constants.DEFAULT_PUBERTY_AGE_FEMALE)
                    parentViewModel.setMenstrualDays(menstrualDays)
                    binding.spacer.visibility = View.GONE
                    binding.femaleTv.visibility = View.VISIBLE
                    binding.femaleCard.visibility = View.VISIBLE
                }
            }
        }

        binding.femaleNp.numberPickerChangeListener =
            object : NumberPicker.OnNumberPickerChangeListener {
                override fun onProgressChanged(
                    numberPicker: NumberPicker,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    parentViewModel.setMenstrualDays(progress)
                }
                override fun onStartTrackingTouch(numberPicker: NumberPicker) {}
                override fun onStopTrackingTouch(numberPicker: NumberPicker) {}
            }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        parentViewModel.setYear(year)
        parentViewModel.setMonth(month)
        parentViewModel.setDay(day)
        setCalendarDate(year, month, day)
    }

    private fun setCalendarDate(year: Int = 2000, month: Int = 1, day: Int = 1) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        val sdf = SimpleDateFormat("dd MMM", Locale("ru"))

        val dayOfMonth = sdf.format(calendar.time)
        binding.dayTv.text = dayOfMonth
        binding.yearTv.text = year.toString()
    }

    private fun setUpCalendarUI() {
        binding.calendarCard.setOnClickListener {
            val year = parentViewModel.year.value!!
            val month = parentViewModel.month.value!!
            val day = parentViewModel.day.value!!
            val dialog = DatePickerDialog(
                binding.root.context,
                R.style.DatePickerTheme,
                this,
                year, month, day
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
        setCalendarDate()
    }

    private fun setUpPubertyAgeNP() {
        parentViewModel.setPubertyAge(binding.pubertyAgeNp.progress)

        binding.pubertyAgeNp.numberPickerChangeListener = object : NumberPicker.OnNumberPickerChangeListener{
            override fun onProgressChanged(
                numberPicker: NumberPicker,
                progress: Int,
                fromUser: Boolean
            ) {
                parentViewModel.setPubertyAge(progress)
            }

            override fun onStartTrackingTouch(numberPicker: NumberPicker) {}
            override fun onStopTrackingTouch(numberPicker: NumberPicker) {}
        }
        binding.forgetChb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                val defaultPubertyAge = when (parentViewModel.gender.value){
                    is Gender.MALE -> Constants.DEFAULT_PUBERTY_AGE_MALE
                    is Gender.FEMALE -> Constants.DEFAULT_PUBERTY_AGE_FEMALE
                    else -> 9
                }
                binding.pubertyAgeNp.setProgress(defaultPubertyAge)
                binding.pubertyAgeNp.isEnabled = false
                binding.pubertyAgeNp.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
            }else{
                binding.pubertyAgeNp.isEnabled = true
                binding.pubertyAgeNp.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
            }
            view?.hideKeyboard()
        }


        binding.titleTv.movementMethod = LinkMovementMethod.getInstance()
        binding.titleTv.setText(addClickablePart(binding.titleTv.text.toString()), TextView.BufferType.SPANNABLE)
    }

    private fun addClickablePart(str: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(str)
        var idx1 = str.indexOf("б")
        var idx2 = 0
        while (idx1 != -1) {
            idx2 = str.indexOf("т", idx1) + 1
            val clickString = str.substring(idx1, idx2)
            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(
                        binding.root.context, clickString,
                        Toast.LENGTH_SHORT
                    ).show()
                    showPlayerDialog()
                }
            }, idx1, idx2, 0)
            idx1 = str.indexOf("[", idx2)
        }
        return ssb
    }

    private fun showPlayerDialog() {

    }

    companion object {


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DatePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}