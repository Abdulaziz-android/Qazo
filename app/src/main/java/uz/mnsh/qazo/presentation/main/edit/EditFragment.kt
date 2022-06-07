package uz.mnsh.qazo.presentation.main.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import uz.mnsh.qazo.common.popBackStack
import uz.mnsh.qazo.databinding.FragmentEditBinding
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.presentation.main.MainActivity
import uz.mnsh.qazo.presentation.main.adapter.PrayerEditAdapter
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var adapter: PrayerEditAdapter
    private val viewModel: EditViewModel by viewModels()
    private val TAG = "EditFragment"
    private var currentList: ArrayList<Prayer> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    @SuppressLint("LogNotTimber")
    private fun observeData() {
        binding.saveBtn.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                Log.d(TAG, "observeData: ${adapter.currentList}")
                viewModel.update(adapter.currentList).collect { state ->
                    when (state) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            Navigation.popBackStack(activity as MainActivity)
                        }
                        is Resource.Error -> {}
                    }
                }
                Log.d(TAG, "observeData: changed data ${adapter.currentList[0].remainingCount}")
                Log.d(TAG, "observeData: current data ${currentList[0].remainingCount}")
            }
        }
        binding.prayerRv.adapter = adapter
        lifecycleScope.launchWhenStarted {
            viewModel.prayerState.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        Log.d(TAG, "observeData: loading")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "observeData: success")

                        val list = state.data
                        if (list != null && list.isNotEmpty()) {
                            if (currentList.isEmpty()) {
                                list.forEach {
                                    currentList.add(
                                        Prayer(
                                            prayerTimeName = it.prayerTimeName,
                                            performedCount = it.performedCount,
                                            remainingCount = it.remainingCount,
                                            date = it.date,
                                            todayPerformedCount = it.todayPerformedCount,
                                            progressColor = it.progressColor,
                                            progressColor20 = it.progressColor20,
                                        )
                                    )
                                }
                            }
                            adapter.submitList(list)
                        }
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "observeData: error")
                    }
                }
            }
        }
    }
}