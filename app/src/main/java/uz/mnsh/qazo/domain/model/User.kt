package uz.mnsh.qazo.domain.model

import uz.mnsh.qazo.common.Gender

data class User(
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
