package uz.mnsh.qazo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mnsh.qazo.domain.model.Prayer

@Dao
interface PrayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(prayer: Prayer)

    @Query("select * from prayer where prayerTimeName = :name")
    suspend fun getPrayerByName(name:String):Prayer?

    @Query("select * from prayer")
    suspend fun getAllPrayers():List<Prayer>?

    @Query("select * from prayer")
    fun getPrayers():LiveData<List<Prayer>>

    @Query("select exists(select * from prayer)")
    fun isExists(): Boolean
}