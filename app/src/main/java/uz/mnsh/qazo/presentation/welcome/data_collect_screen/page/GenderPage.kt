package uz.mnsh.qazo.presentation.welcome.data_collect_screen.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import uz.mnsh.qazo.R
import uz.mnsh.qazo.common.Gender
import uz.mnsh.qazo.databinding.PageGenderBinding
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.DataCollectViewModel

class GenderPage : Fragment() {


    private var _binding: PageGenderBinding? =null
    private val binding get() = _binding!!
    private val parentViewModel: DataCollectViewModel by viewModels({ requireParentFragment() })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageGenderBinding.inflate(layoutInflater, container, false)

        setGenderPage()

        return binding.root
    }

    private fun setGenderPage() {

        binding.radioGroup.setOnCheckedChangeListener { _, p1 ->
            val gender: Gender? = when (p1) {
                R.id.male_rb -> Gender.MALE
                R.id.female_rb -> Gender.FEMALE
                else -> null
            }
            gender?.let { parentViewModel.setGender(it) }
        }
    }

}