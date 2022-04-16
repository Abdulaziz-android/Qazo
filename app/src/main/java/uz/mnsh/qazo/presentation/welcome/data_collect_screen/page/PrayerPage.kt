package uz.mnsh.qazo.presentation.welcome.data_collect_screen.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import it.sephiroth.android.library.numberpicker.NumberPicker
import uz.mnsh.qazo.databinding.PagePrayerBinding
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.DataCollectViewModel

class PrayerPage : Fragment() {

    private var _binding: PagePrayerBinding? = null
    private val binding get() = _binding!!
    private val parentViewModel: DataCollectViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PagePrayerBinding.inflate(inflater, container, false)


        parentViewModel.pagePosition.observe(viewLifecycleOwner) {
            if (it == 2) {
                val daysDatePage = parentViewModel.getDaysDatePage()
                prepareNumberPickers(daysDatePage)
            }
        }

        return binding.root
    }

    private val TAG = "PrayerPage"
    private fun prepareNumberPickers(daysDatePage: Int) {
        binding.yearNp.progress = 0
        binding.monthNp.progress = 0
        binding.dayNp.progress = 0

        val yearMax = (daysDatePage / 365)
        Log.d(TAG, "prepareNumberPickers: $daysDatePage $yearMax")
        binding.yearNp.maxValue = yearMax

        binding.yearNp.numberPickerChangeListener = yearListener
        binding.monthNp.numberPickerChangeListener = monthListener
        binding.dayNp.numberPickerChangeListener = dayListener

       /* if (daysDatePage <= 30) {
            binding.dayNp.maxValue = daysDatePage.toInt()
            binding.monthNp.maxValue = 0
            binding.yearNp.maxValue = 0
        } else if (daysDatePage < 365) {
            binding.dayNp.maxValue = 30
            binding.monthNp.maxValue = (daysDatePage / (365.25 / 12)).toInt()
            binding.yearNp.maxValue = 0
        } else {
            binding.dayNp.maxValue = 30
            binding.monthNp.maxValue = 11
            binding.yearNp.maxValue = (daysDatePage / 365.25).toInt()
        }*/
    }

    private val yearListener = object : NumberPicker.OnNumberPickerChangeListener{
        override fun onProgressChanged(
            numberPicker: NumberPicker,
            progress: Int,
            fromUser: Boolean
        ) {
            parentViewModel.setPerformedYear(progress)
        }
        override fun onStartTrackingTouch(numberPicker: NumberPicker) {}
        override fun onStopTrackingTouch(numberPicker: NumberPicker) {}
    }

    private val monthListener = object : NumberPicker.OnNumberPickerChangeListener{
        override fun onProgressChanged(
            numberPicker: NumberPicker,
            progress: Int,
            fromUser: Boolean
        ) {
            parentViewModel.setPerformedMonth(progress)
        }
        override fun onStartTrackingTouch(numberPicker: NumberPicker) {}
        override fun onStopTrackingTouch(numberPicker: NumberPicker) {}
    }

    private val dayListener = object : NumberPicker.OnNumberPickerChangeListener{
        override fun onProgressChanged(
            numberPicker: NumberPicker,
            progress: Int,
            fromUser: Boolean
        ) {
            parentViewModel.setPerformedDay(progress)
        }
        override fun onStartTrackingTouch(numberPicker: NumberPicker) {}
        override fun onStopTrackingTouch(numberPicker: NumberPicker) {}
    }

}