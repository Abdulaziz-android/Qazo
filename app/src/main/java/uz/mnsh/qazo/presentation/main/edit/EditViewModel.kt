package uz.mnsh.qazo.presentation.main.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.use_case.UserUseCases
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _prayersState =
        MutableStateFlow<Resource<List<Prayer>>>(Resource.Loading<List<Prayer>>())
    val prayerState: StateFlow<Resource<List<Prayer>>> = _prayersState.asStateFlow()

    init {
        viewModelScope.launch {
            userUseCases.getPrayers().collect {
                _prayersState.value = it
            }
        }
    }


    fun update(list: List<Prayer>): Flow<Resource<String>> {
        return userUseCases.updatePrayers(list)
    }


}