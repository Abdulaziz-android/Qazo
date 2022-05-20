package uz.mnsh.qazo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.mnsh.qazo.domain.model.Statistic

@Dao
interface StatisticDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(statistic: Statistic)

    @Query("select * from statistic where date = :date")
    suspend fun getStatisticByDate(date:String):Statistic?

    @Query("select exists(select * from statistic)")
    fun isExists(): Boolean
}