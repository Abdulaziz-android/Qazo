package uz.mnsh.qazo.data.local.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mnsh.qazo.domain.model.Prayer
import java.lang.reflect.Type

class PrayerListConvertor {

    @TypeConverter
    fun fromStringList(list: List<Prayer?>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Prayer?>?>() {}.type
        return gson.toJson(list, type)
    }


    @TypeConverter
    fun toStringList(statisticString: String?): List<Prayer>? {
        if (statisticString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Prayer?>?>() {}.type
        return gson.fromJson<List<Prayer>>(statisticString, type)
    }
}
