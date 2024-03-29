package uz.mnsh.qazo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Prayer(
    @PrimaryKey
    val prayerTimeName:String,
    val range:Int,
    var performedCount:Int,
    var remainingCount:Int,
    var date:String,
    var todayPerformedCount:Int,
    val progressColor:String,
    val progressColor20:String
)
