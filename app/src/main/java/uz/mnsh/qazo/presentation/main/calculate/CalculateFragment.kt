package uz.mnsh.qazo.presentation.main.calculate

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
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.mnsh.qazo.R
import uz.mnsh.qazo.databinding.FragmentCalculateBinding
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.presentation.main.MainActivity
import uz.mnsh.qazo.presentation.main.adapter.PrayerAdapter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CalculateFragment : Fragment() {

    private var _binding: FragmentCalculateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculateViewModel by viewModels()
    @Inject lateinit var adapter: PrayerAdapter
    private val TAG = "CalculateFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculateBinding.inflate(inflater, container, false)
        observeData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("LogNotTimber")
    private fun observeData() {
        binding.prayerRv.adapter = adapter
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.prayerState.collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            state.data?.let { prayers ->
                                adapter.submitList(prayers)
                                setUpCard(prayers)
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(binding.root.context, state.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpCard(list: List<Prayer>) {

        Log.d(TAG, "setUpCard: $list")
        var isOld = false
        val today = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(Date())

        binding.apply {
            var todayCount = 0
            var allPerformed = 0
            var allRemained = 0

            list.forEach { prayer ->
                if (prayer.date == today) {
                    todayCount += prayer.todayPerformedCount
                }else{
                    isOld = true
                }
                allPerformed += prayer.performedCount
                allRemained += prayer.remainingCount
            }

            allCountTv.text = "$allRemained қазо, $allPerformed ўқилди"
            todayCountTv.text = todayCount.toString()

            progressBar.max = allRemained
            progressBar.progress = allPerformed

            percentTv.text = ((allPerformed*100)/allRemained).toString()

            prayerCard.setOnClickListener {
                Navigation.findNavController(activity as MainActivity , R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_edit)
            }

            if (isOld){
                viewModel.updateDate(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}