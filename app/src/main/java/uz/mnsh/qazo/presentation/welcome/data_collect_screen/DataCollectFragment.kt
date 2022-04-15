package uz.mnsh.qazo.presentation.welcome.data_collect_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import uz.mnsh.qazo.databinding.FragmentDataCollectBinding
import uz.mnsh.qazo.presentation.welcome.adapter.PagerAdapter

class DataCollectFragment : Fragment() {

    private var _binding: FragmentDataCollectBinding? =null
    private val binding get() = _binding!!
    private lateinit var adapter:PagerAdapter
    private val viewModel: DataCollectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataCollectBinding.inflate(layoutInflater, container, false)

        adapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        binding.indicator.setViewPager2(binding.viewPager)

        binding.positiveBtn.setOnClickListener {
            when {
                viewModel.gender.value == null -> {
                    Toast.makeText(binding.root.context, "Жинс танланмаган!", Toast.LENGTH_SHORT).show()
                }
                binding.viewPager.currentItem == 2 -> {
                    Toast.makeText(binding.root.context, "", Toast.LENGTH_SHORT).show()
                }
                else -> binding.viewPager.currentItem += 1
            }
        }

        return binding.root
    }
}