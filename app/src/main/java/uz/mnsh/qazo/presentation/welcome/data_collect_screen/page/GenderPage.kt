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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GenderPage : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GenderPage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}