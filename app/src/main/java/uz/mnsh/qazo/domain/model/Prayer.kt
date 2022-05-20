package uz.mnsh.qazo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Prayer(
    @PrimaryKey
    val prayerTimeName:String,
    var performedCount:Int,
    var remainingCount:Int,
    val date:String,
    var todayPerformedCount:Int,
    val progressColor:String,
    val progressColor20:String
)
