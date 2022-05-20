package uz.mnsh.qazo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Statistic(
    @PrimaryKey
    val prayerTimeName:String,
    val date: String,
    val max: Int,
    var prayedCount: Int = 0
)