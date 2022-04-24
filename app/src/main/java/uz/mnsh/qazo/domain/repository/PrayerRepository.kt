package uz.mnsh.qazo.domain.repository

import uz.mnsh.qazo.domain.model.User

interface PrayerRepository {

    suspend fun getUser():User

    fun insertUser(user: User)


}