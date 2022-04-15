package uz.mnsh.qazo.presentation.welcome.intro_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.mnsh.qazo.R
import uz.mnsh.qazo.databinding.FragmentIntroBinding
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.DataCollectFragment

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? =null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(layoutInflater, container, false)

        binding.negativeBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DataCollectFragment()).addToBackStack("intro").commit()
        }


        return binding.root
    }

}