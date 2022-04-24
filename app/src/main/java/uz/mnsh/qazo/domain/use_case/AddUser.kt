package uz.mnsh.qazo.domain.use_case

import uz.mnsh.qazo.domain.model.User
import uz.mnsh.qazo.domain.repository.PrayerRepository

class AddUser(
    private val repository: PrayerRepository
) {
    operator fun invoke(user: User ){
        repository.insertUser(user)
    }
}