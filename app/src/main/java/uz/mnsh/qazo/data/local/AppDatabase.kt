package uz.mnsh.qazo.data.local

import androidx.room.Database
import uz.mnsh.qazo.data.local.dao.UserDao
import uz.mnsh.qazo.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase {

    abstract fun userDao():UserDao

}