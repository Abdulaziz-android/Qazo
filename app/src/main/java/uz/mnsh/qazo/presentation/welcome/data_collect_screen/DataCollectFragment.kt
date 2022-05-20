package uz.mnsh.qazo.presentation.welcome.data_collect_screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.mnsh.qazo.databinding.FragmentDataCollectBinding
import uz.mnsh.qazo.presentation.main.MainActivity
import uz.mnsh.qazo.presentation.welcome.WelcomeActivity
import uz.mnsh.qazo.presentation.welcome.adapter.PagerAdapter

@AndroidEntryPoint
class DataCollectFragment : Fragment() {

    private var _binding: FragmentDataCollectBinding? =null
    private val binding get() = _binding!!
    private lateinit var adapter:PagerAdapter
    private val viewModel: DataCollectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataCollectBinding.inflate(inflater, container, false)

        adapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        binding.indicator.setViewPager2(binding.viewPager)

        binding.positiveBtn.setOnClickListener {
            when {
                viewModel.gender.value!!.isEmpty() -> {
                    Toast.makeText(binding.root.context, "Жинс танланмаган!", Toast.LENGTH_SHORT).show()
                }
                binding.viewPager.currentItem == 2 -> {
                    viewModel.saveAllData()
                    (activity as WelcomeActivity?)?.finish()
                    startActivity(Intent(binding.root.context, MainActivity::class.java))
                }
                else -> {
                    binding.viewPager.currentItem += 1
                    viewModel.setPagePosition(binding.viewPager.currentItem)
                }
            }
        }

        return binding.root
    }
}