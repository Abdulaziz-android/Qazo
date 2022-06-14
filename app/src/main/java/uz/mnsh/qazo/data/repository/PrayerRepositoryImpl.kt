package uz.mnsh.qazo.data.repository

import androidx.lifecycle.LiveData
import uz.mnsh.qazo.data.local.dao.PrayerDao
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.repository.PrayerRepository

class PrayerRepositoryImpl(private val prayerDao: PrayerDao) : PrayerRepository {

    override fun insert(prayer: Prayer) {
        prayerDao.insert(prayer)
    }

    override suspend fun getAllPrayers(): List<Prayer>? {
        return prayerDao.getAllPrayers()
    }

    override suspend fun getPrayerByName(name: String): Prayer? {
        return prayerDao.getPrayerByName(name)
    }

    override fun getPrayers(): LiveData<List<Prayer>> {
        return prayerDao.getPrayers()
    }

}