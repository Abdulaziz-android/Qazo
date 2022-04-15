package uz.mnsh.qazo.presentation.welcome.data_collect_screen.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import uz.mnsh.qazo.R
import uz.mnsh.qazo.databinding.PagePrayerBinding
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.DataCollectViewModel

class PrayerPage : Fragment() {

    private var _binding: PagePrayerBinding? =null
    private val binding get() = _binding!!
    private val parentViewModel: DataCollectViewModel by viewModels({ requireParentFragment() })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PagePrayerBinding.inflate(layoutInflater, container, false)



        return binding.root
    }

}