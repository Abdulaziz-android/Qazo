package uz.mnsh.qazo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id:Int = 1,
    val gender:String,
    var birthDate:String = "",
    var pubertyAge:Int = 9,
    val menstrualDays:Int = 0
)
