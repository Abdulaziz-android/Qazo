package uz.mnsh.qazo.presentation.welcome.data_collect_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.mnsh.qazo.common.Gender
import uz.mnsh.qazo.domain.model.User
import java.util.*
import java.util.concurrent.TimeUnit

class DataCollectViewModel : ViewModel() {

    private val TAG = "DataCollectViewModel"
    //3189 2943 2453

    private val _pagePosition = MutableLiveData(0)
    var pagePosition: LiveData<Int> = _pagePosition

    private val _gender = MutableLiveData<Gender>()
    var gender: LiveData<Gender> = _gender

    private val _birthDate = MutableLiveData<String>()
    var birthDate: LiveData<String> = _birthDate

    private val _pubertyAge = MutableLiveData<Int>()
    var pubertyAge: LiveData<Int> = _pubertyAge

    private val _menstrualDays = MutableLiveData<Int>()
    var menstrualDays: LiveData<Int> = _menstrualDays

    private val _year = MutableLiveData<Int>(2000)
    var year: LiveData<Int> = _year

    private val _month = MutableLiveData<Int>(1)
    var month: LiveData<Int> = _month

    private val _day = MutableLiveData<Int>(1)
    var day: LiveData<Int> = _day

    fun setPagePosition(position: Int) {
        _pagePosition.value = position
    }

    fun setGender(gender: Gender) {
        _gender.value = gender
    }

    fun setDate(birthDate: String) {
        _birthDate.value = birthDate
    }

    fun setPubertyAge(puberty: Int) {
        _pubertyAge.value = puberty
    }

    fun setMenstrualDays(menstrualDays: Int) {
        _menstrualDays.value = menstrualDays
    }

    fun setYear(year: Int) {
        _year.value = year
    }

    fun setMonth(month: Int) {
        _month.value = month
    }

    fun setDay(day: Int) {
        _day.value = day
    }

    fun getUser(): User {
        return User(
            gender = _gender.value!!,
            birthDate = _birthDate.value ?: "",
            pubertyAge = _pubertyAge.value ?: 9,
            menstrualDays = _menstrualDays.value ?: if (_gender.value == Gender.FEMALE) 6 else 0,
        )
    }

    fun getDaysDatePage(): Long {

        val oldDate = Calendar.getInstance()
        val mYear = year.value!! + pubertyAge.value!!
        val mMonth = month.value!!
        val mDay = day.value!!
        oldDate.set(Calendar.YEAR, mYear)
        oldDate.set(Calendar.MONTH, mMonth)
        oldDate.set(Calendar.DAY_OF_MONTH, mDay)

        val nowDate = Calendar.getInstance()

        val diff = nowDate.timeInMillis - oldDate.timeInMillis
        var days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        Log.d(TAG, "getDaysAfterDatePage: ${pubertyAge.value!!} and $days")

        if (gender.value == Gender.FEMALE) {
            Log.d(TAG, "getDaysAfterDatePage: ${pubertyAge.value!!} and $days")
            val allMenstrualDays = ((days/365.25)*12) * menstrualDays.value!!
            Log.d(TAG, "getDaysAfterDatePage: ${menstrualDays.value!!} and $allMenstrualDays")
            days -= allMenstrualDays.toLong()
        }

        return days
    }

}