package uz.mnsh.qazo.presentation.welcome.data_collect_screen.page

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import it.sephiroth.android.library.numberpicker.NumberPicker
import uz.mnsh.qazo.R
import uz.mnsh.qazo.common.Constants
import uz.mnsh.qazo.databinding.DialogPlayerBinding
import uz.mnsh.qazo.databinding.PageDateBinding
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.DataCollectViewModel
import java.text.SimpleDateFormat
import java.util.*

class DatePage : Fragment(), DatePickerDialog.OnDateSetListener {

    private var _binding: PageDateBinding? = null
    private val binding get() = _binding!!
    private val parentViewModel: DataCollectViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageDateBinding.inflate(inflater, container, false)

        parentViewModel.pagePosition.observe(viewLifecycleOwner) { position ->
            if (position == 1) {
                setDatePage()
            }
        }

        return binding.root
    }

    private fun setDatePage() {
        setUpFemaleWindow()
        setUpPubertyAgeNP()
        setUpCalendarUI()
    }


    private fun setUpFemaleWindow() {
        when (parentViewModel.gender.value) {
            Constants.MALE -> {
                binding.pubertyAgeNp.progress = Constants.DEFAULT_PUBERTY_AGE_MALE
                parentViewModel.setMenstrualDays(0)
                binding.spacer.visibility = View.VISIBLE
                binding.femaleTv.visibility = View.GONE
                binding.femaleCard.visibility = View.GONE
            }
            Constants.FEMALE -> {
                val menstrualDays = binding.femaleNp.progress
                binding.pubertyAgeNp.progress = Constants.DEFAULT_PUBERTY_AGE_FEMALE
                parentViewModel.setMenstrualDays(menstrualDays)
                binding.spacer.visibility = View.GONE
                binding.femaleTv.visibility = View.VISIBLE
                binding.femaleCard.visibility = View.VISIBLE
            }
            else -> {}
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
        val bsdf = SimpleDateFormat(Constants.BIRTH_DATE_PATTERN, Locale("ru"))

        val dayOfMonth = sdf.format(calendar.time)
        binding.dayTv.text = dayOfMonth
        binding.yearTv.text = year.toString()

        val birthDate = bsdf.format(calendar.time)
        parentViewModel.setDate(birthDate)
/*
        val diff = year - Calendar.getInstance().get(Calendar.YEAR)
        if (diff<15 && diff>9){
            binding.pubertyAgeNp.maxValue = diff
        }else {
            binding.pubertyAgeNp.maxValue = 15
        }*/
    }

    private fun setUpCalendarUI() {
        val year = parentViewModel.year.value!!
        val month = parentViewModel.month.value!!
        val day = parentViewModel.day.value!!

        val dialog = DatePickerDialog(
            binding.root.context,
            R.style.DatePickerTheme,
            this,
            year, month, day
        )
        binding.calendarCard.setOnClickListener {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
        val cal = Calendar.getInstance()
        val calNow = Calendar.getInstance()
        val cYear =
            if (parentViewModel.gender.value == Constants.MALE) Constants.DEFAULT_PUBERTY_AGE_MALE
            else Constants.DEFAULT_PUBERTY_AGE_FEMALE
        cal.set(Calendar.YEAR, calNow.get(Calendar.YEAR) - cYear)
        dialog.datePicker.maxDate = cal.timeInMillis

        setCalendarDate()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpPubertyAgeNP() {
        binding.pubertyAgeNp.numberPickerChangeListener =
            object : NumberPicker.OnNumberPickerChangeListener {
                override fun onProgressChanged(
                    numberPicker: NumberPicker,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    /*  val calBirth = Calendar.getInstance()
                      val calNow = Calendar.getInstance()
                      val sdf = SimpleDateFormat(Constants.BIRTH_DATE_PATTERN, Locale.ENGLISH)
                      calBirth.time = sdf.parse(parentViewModel.birthDate.value!!)!!
                      val year = calNow.get(Calendar.YEAR) - calBirth.get(Calendar.YEAR)
                      if (year<progress) {
                          parentViewModel.setPubertyAge(progress)
                      }else{
                          binding.pubertyAgeNp.progress = parentViewModel.pubertyAge.value!!
                      }
  */
                    parentViewModel.setPubertyAge(progress)
                }

                override fun onStartTrackingTouch(numberPicker: NumberPicker) {}
                override fun onStopTrackingTouch(numberPicker: NumberPicker) {}
            }
        binding.forgetChb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val defaultPubertyAge = when (parentViewModel.gender.value) {
                    Constants.MALE -> Constants.DEFAULT_PUBERTY_AGE_MALE
                    Constants.FEMALE -> Constants.DEFAULT_PUBERTY_AGE_FEMALE
                    else -> 9
                }
                binding.pubertyAgeNp.setProgress(defaultPubertyAge)
                binding.pubertyAgeNp.isEnabled = false
                binding.pubertyAgeNp.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
            } else {
                binding.pubertyAgeNp.isEnabled = true
                binding.pubertyAgeNp.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
            }
            view?.hideKeyboard()
        }
        parentViewModel.setPubertyAge(binding.pubertyAgeNp.progress)

        binding.titleTv.movementMethod = LinkMovementMethod.getInstance()
        binding.titleTv.setText(
            addClickablePart(binding.titleTv.text.toString()),
            TextView.BufferType.SPANNABLE
        )
    }

    private fun addClickablePart(str: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(str)
        var idx1 = str.indexOf("б")
        var idx2: Int
        while (idx1 != -1) {
            idx2 = str.indexOf("т", idx1) + 1
            //  val clickString = str.substring(idx1, idx2)
            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    showPlayerDialog()
                }
            }, idx1, idx2, 0)
            idx1 = str.indexOf("[", idx2)
        }
        return ssb
    }

    private fun showPlayerDialog() {
        val dialog = AlertDialog.Builder(binding.root.context)

        val view = DialogPlayerBinding.inflate(layoutInflater)
        dialog.setView(view.root)

        val mplayer = MediaPlayer.create(context, R.raw.puberty)

        view.playIv.setOnClickListener {
            if (mplayer.isPlaying) {
                mplayer.pause()
                view.playIv.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
            } else {
                mplayer.start()
                mplayer.setOnCompletionListener {
                    view.playIv.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                }
                view.playIv.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
            }
        }

        dialog.setOnCancelListener {
            mplayer.release()
        }

        dialog.show()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}