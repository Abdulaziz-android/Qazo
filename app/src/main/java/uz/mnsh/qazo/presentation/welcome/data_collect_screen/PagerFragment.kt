package uz.mnsh.qazo.presentation.welcome.data_collect_screen

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import it.sephiroth.android.library.numberpicker.NumberPicker
import uz.mnsh.qazo.R
import uz.mnsh.qazo.common.Constants
import uz.mnsh.qazo.common.Gender
import uz.mnsh.qazo.databinding.FragmentPagerBinding
import uz.mnsh.qazo.databinding.PageDateBinding
import uz.mnsh.qazo.databinding.PageGenderBinding
import uz.mnsh.qazo.databinding.PagePrayerBinding
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PagerFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var param1: String? = null
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            position = it.getInt(ARG_PARAM2)
        }
    }

    private var _binding: FragmentPagerBinding? = null
    private val binding get() = _binding!!
    private val parentViewModel: DataCollectViewModel by viewModels({ requireParentFragment() })
    private val TAG = "PagerFragment"
    private var _pageDateBinding: PageDateBinding? = null
    private val pageDateBinding get() = _pageDateBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagerBinding.inflate(layoutInflater, container, false)

        when (position) {
            0 -> {
                setGenderPage()
            }
            1 -> {
                setDatePage()
            }
            2 -> {
                setPrayerPage()
            }
        }

        return binding.root
    }

    private fun setGenderPage() {
        binding.viewStub.layoutResource = R.layout.page_gender
        val view = binding.viewStub.inflate()
        val pageBinding = PageGenderBinding.bind(view)

        pageBinding.radioGroup.setOnCheckedChangeListener { _, p1 ->
            val gender: Gender? = when (p1) {
                R.id.male_rb -> Gender.MALE
                R.id.female_rb -> Gender.FEMALE
                else -> null
            }
            gender?.let { parentViewModel.setGender(it) }
        }
    }

    private fun setDatePage() {
        binding.viewStub.layoutResource = R.layout.page_date
        val view = binding.viewStub.inflate()
        _pageDateBinding = PageDateBinding.bind(view)

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
                    pageDateBinding.spacer.visibility = View.VISIBLE
                    pageDateBinding.femaleTv.visibility = View.GONE
                    pageDateBinding.femaleCard.visibility = View.GONE
                }
                is Gender.FEMALE -> {
                    val menstrualDays = pageDateBinding.femaleNp.progress
                    parentViewModel.setPubertyAge(Constants.DEFAULT_PUBERTY_AGE_FEMALE)
                    parentViewModel.setMenstrualDays(menstrualDays)
                    pageDateBinding.spacer.visibility = View.GONE
                    pageDateBinding.femaleTv.visibility = View.VISIBLE
                    pageDateBinding.femaleCard.visibility = View.VISIBLE
                }
            }
        }

        pageDateBinding.femaleNp.numberPickerChangeListener =
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
        pageDateBinding.dayTv.text = dayOfMonth
        pageDateBinding.yearTv.text = year.toString()
    }

    private fun setUpCalendarUI() {
        pageDateBinding.calendarCard.setOnClickListener {
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
        parentViewModel.setPubertyAge(pageDateBinding.pubertyAgeNp.progress)

        pageDateBinding.pubertyAgeNp.numberPickerChangeListener = object : NumberPicker.OnNumberPickerChangeListener{
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
        pageDateBinding.forgetChb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                val defaultPubertyAge = when (parentViewModel.gender.value){
                    is Gender.MALE -> Constants.DEFAULT_PUBERTY_AGE_MALE
                    is Gender.FEMALE -> Constants.DEFAULT_PUBERTY_AGE_FEMALE
                    else -> 9
                }
                pageDateBinding.pubertyAgeNp.setProgress(defaultPubertyAge)
                pageDateBinding.pubertyAgeNp.isEnabled = false
                pageDateBinding.pubertyAgeNp.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
            }else{
                pageDateBinding.pubertyAgeNp.isEnabled = true
                pageDateBinding.pubertyAgeNp.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
            }
            view?.hideKeyboard()
        }


        pageDateBinding.titleTv.movementMethod = LinkMovementMethod.getInstance()
        pageDateBinding.titleTv.setText(addClickablePart(pageDateBinding.titleTv.text.toString()), TextView.BufferType.SPANNABLE)
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
                }
            }, idx1, idx2, 0)
            idx1 = str.indexOf("[", idx2)
        }
        return ssb
    }

    private fun setPrayerPage() {
        binding.viewStub.layoutResource = R.layout.page_prayer
        val view = binding.viewStub.inflate()
        val pageBinding = PagePrayerBinding.bind(view)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: Int) =
            PagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}