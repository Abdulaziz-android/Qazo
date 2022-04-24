package uz.mnsh.qazo.data.local.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mnsh.qazo.common.Gender
import java.lang.reflect.Type

class GenderConvertor {
    @TypeConverter
    fun fromStringGender(gender: Gender?): String? {
        if (gender == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Gender?>() {}.type
        return gson.toJson(gender, type)
    }


    @TypeConverter
    fun toStringGender(statisticString: String?): Gender? {
        if (statisticString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Gender?>() {}.type
        return gson.fromJson<Gender>(statisticString, type)
    }
}
