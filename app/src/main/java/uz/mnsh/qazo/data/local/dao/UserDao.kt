package uz.mnsh.qazo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.mnsh.qazo.domain.model.User

@Dao
interface UserDao {

    @Insert()
    fun insert(user:User)

    @Query("select * from user where id = 1")
    fun getUser():User

}