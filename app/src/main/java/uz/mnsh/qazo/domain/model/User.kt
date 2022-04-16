package uz.mnsh.qazo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.mnsh.qazo.common.Gender

@Entity
data class User(
    @PrimaryKey
    val id:Int = 1,
    var gender:Gender = Gender.MALE,
    var birthDate:String = "",
    var pubertyAge:Int = 9,
    val menstrualDays:Int = 0,
    var fajr:Int = 0,
    var dhuhr:Int = 0,
    var asr:Int = 0,
    var maghrib:Int = 0,
    var isha:Int = 0,
)
