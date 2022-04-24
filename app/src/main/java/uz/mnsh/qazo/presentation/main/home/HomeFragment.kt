package uz.mnsh.qazo.presentation.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.mnsh.qazo.R
import uz.mnsh.qazo.databinding.FragmentHomeBinding
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.model.User
import uz.mnsh.qazo.presentation.main.adapter.PrayerAdapter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    @Inject lateinit var adapter: PrayerAdapter
    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        observeData()


        return binding.root
    }

    @SuppressLint("LogNotTimber")
    private fun observeData() {
        binding.prayerRv.adapter = adapter
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.state.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        Log.d(TAG, "observeData: ")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "observeData: ${state.data}")
                        state.data?.let { getPrayerList(it) }
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "observeData: ${state.message}")
                        Toast.makeText(binding.root.context, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getPrayerList(user: User) {
        val list = arrayListOf<Prayer>()
        val prayerTimes = resources.getStringArray(R.array.PrayerTimes)
        prayerTimes.forEach {
            list.add(Prayer(name = it))
        }
        list[0].count = user.fajr
        list[1].count = user.dhuhr
        list[2].count = user.asr
        list[3].count = user.maghrib
        list[4].count = user.isha

        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}