package uz.mnsh.qazo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.mnsh.qazo.data.local.convertor.PrayerListConvertor
import uz.mnsh.qazo.data.local.dao.PrayerDao
import uz.mnsh.qazo.data.local.dao.UserDao
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.model.Statistic
import uz.mnsh.qazo.domain.model.User

@Database(entities = [User::class, Statistic::class, Prayer::class], version = 1)
@TypeConverters(PrayerListConvertor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun prayerDao():PrayerDao

    companion object{
        const val DATABASE = "user_db"
    }

}