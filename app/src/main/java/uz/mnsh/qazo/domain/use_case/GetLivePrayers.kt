package uz.mnsh.qazo.domain.use_case

import androidx.lifecycle.LiveData
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.repository.PrayerRepository

class GetLivePrayers(
    private val repository: PrayerRepository
) {
    operator fun invoke(): LiveData<List<Prayer>> = repository.getPrayers()
}