package uz.mnsh.qazo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import uz.mnsh.qazo.common.Gender
import uz.mnsh.qazo.data.local.convertor.GenderConvertor
import uz.mnsh.qazo.data.local.convertor.PrayerListConvertor

@Entity
data class User(
    @PrimaryKey
    val id:Int = 1,
   // @TypeConverters(GenderConvertor::class)
  //  var gender:String = Gender.MALE,
    var birthDate:String = "",
    var pubertyAge:Int = 9,
    val menstrualDays:Int = 0,
    var fajr:Int = 0,
    var dhuhr:Int = 0,
    var asr:Int = 0,
    var maghrib:Int = 0,
    var isha:Int = 0,
    @TypeConverters(PrayerListConvertor::class)
    var prayer:List<Prayer> = arrayListOf()
)
