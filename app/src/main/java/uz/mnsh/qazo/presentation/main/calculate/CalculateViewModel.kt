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
import javax.inject.Inject

@HiltViewModel
class CalculateViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
/*

    private val _statisticState = MutableStateFlow<Resource<Statistic>>(Resource.Loading<Statistic>())
    val statisticState : StateFlow<Resource<Statistic>> = _statisticState.asStateFlow()
*/
/*

    private val _userState = MutableStateFlow<Resource<User>>(Resource.Loading<User>())
    val userState: StateFlow<Resource<User>> = _userState.asStateFlow()

    init {
        viewModelScope.launch {
            userUseCases.getUser().collect {
                _userState.value = it
            }
        }
    }


    fun update(prayer: Prayer) {
        userUseCases.addPrayer(prayer)
    }
*/


    private val _prayersState = MutableStateFlow<Resource<List<Prayer>>>(Resource.Loading<List<Prayer>>())
    val prayerState: StateFlow<Resource<List<Prayer>>> = _prayersState.asStateFlow()


    init {
        viewModelScope.launch {
            userUseCases.getPrayers().collect {
                _prayersState.value = it
            }
        }
    }
}