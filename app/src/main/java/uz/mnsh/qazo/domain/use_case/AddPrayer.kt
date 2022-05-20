package uz.mnsh.qazo.domain.use_case

import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.repository.PrayerRepository

class AddPrayer(
    private val repository: PrayerRepository
) {
    operator fun invoke(prayer: Prayer){
        repository.insert(prayer)
    }
}