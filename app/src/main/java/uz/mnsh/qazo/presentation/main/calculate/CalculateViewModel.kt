package uz.mnsh.qazo.presentation.main.calculate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.model.User
import uz.mnsh.qazo.domain.use_case.UserUseCases
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalculateViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _prayersState =
        MutableStateFlow<Resource<List<Prayer>>>(Resource.Loading<List<Prayer>>())
    val prayerState: StateFlow<Resource<List<Prayer>>> = _prayersState.asStateFlow()


    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            userUseCases.getPrayers().collect {
                _prayersState.value = it
            }
        }
    }

    fun updateDate(list: List<Prayer>) {
        val today = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(Date())

        list.forEach { prayer ->
            if (prayer.date != today) {
                prayer.date = today
                prayer.todayPerformedCount = 0
            }
        }

        userUseCases.updatePrayers(list)
    }

}