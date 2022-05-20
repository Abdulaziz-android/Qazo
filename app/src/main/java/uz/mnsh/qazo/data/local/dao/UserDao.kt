package uz.mnsh.qazo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mnsh.qazo.domain.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user:User)

    @Query("select * from user where id = 1")
    fun getUser():User?

    @Query("select exists(select * from user)")
    fun isExists(): Boolean

}