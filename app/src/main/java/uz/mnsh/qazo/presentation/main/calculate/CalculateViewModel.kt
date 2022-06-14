package uz.mnsh.qazo.presentation.main.calculate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.mnsh.qazo.data.local.dao.PrayerDao
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.use_case.UserUseCases
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalculateViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

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

    fun getPrayers(): LiveData<List<Prayer>> {
        return userUseCases.getLivePrayers()
    }

    fun updatePrayer(prayer: Prayer){
        userUseCases.addPrayer(prayer)
    }

    /*   private val _prayersState =
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
*/
}