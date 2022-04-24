package uz.mnsh.qazo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.mnsh.qazo.data.local.convertor.GenderConvertor
import uz.mnsh.qazo.data.local.convertor.PrayerListConvertor
import uz.mnsh.qazo.data.local.dao.UserDao
import uz.mnsh.qazo.domain.model.User

@Database(entities = [User::class], version = 1)
@TypeConverters(GenderConvertor::class, PrayerListConvertor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao():UserDao

    companion object{
        const val DATABASE = "user_db"
    }
}