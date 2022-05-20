package uz.mnsh.qazo.domain.repository

import uz.mnsh.qazo.domain.model.Prayer

interface PrayerRepository {

    fun insert(prayer: Prayer)

    suspend fun getAllPrayers():List<Prayer>?

    suspend fun getPrayerByName(name:String):Prayer?


}