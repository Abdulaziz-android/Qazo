package uz.mnsh.qazo.presentation.welcome.data_collect_screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.mnsh.qazo.R
import uz.mnsh.qazo.common.Constants
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.model.User
import uz.mnsh.qazo.domain.use_case.UserUseCases
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DataCollectViewModel @SuppressLint("StaticFieldLeak")
@Inject constructor(
    private var userUseCases: UserUseCases,
     @ApplicationContext private val  context: Context
) : ViewModel() {

    private val TAG = "DataCollectViewModel"

    private val _pagePosition = MutableLiveData(0)
    var pagePosition: LiveData<Int> = _pagePosition

    private val _gender = MutableLiveData<String>("")
    var gender: LiveData<String> = _gender

    private val _birthDate = MutableLiveData<String>()
    var birthDate: LiveData<String> = _birthDate

    private val _pubertyAge = MutableLiveData<Int>()
    var pubertyAge: LiveData<Int> = _pubertyAge

    private val _menstrualDays = MutableLiveData<Int>()
    var menstrualDays: LiveData<Int> = _menstrualDays

    private val _year = MutableLiveData(2000)
    var year: LiveData<Int> = _year

    private val _month = MutableLiveData(1)
    var month: LiveData<Int> = _month

    private val _day = MutableLiveData(1)
    var day: LiveData<Int> = _day

    private val _yearPerformed = MutableLiveData(0)
    var yearPerformed: LiveData<Int> = _yearPerformed

    private val _monthPerformed = MutableLiveData(0)
    var monthPerformed: LiveData<Int> = _monthPerformed

    private val _dayPerformed = MutableLiveData(0)
    var dayPerformed: LiveData<Int> = _dayPerformed

    fun setPagePosition(position: Int) {
        _pagePosition.value = position
    }

    fun setGender(gender: String) {
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

    fun setPerformedYear(year: Int) {
        _yearPerformed.value = year
    }

    fun setPerformedMonth(month: Int) {
        _monthPerformed.value = month
    }

    fun setPerformedDay(day: Int) {
        _dayPerformed.value = day
    }

    fun saveAllData() {
        saveUser()
        savePrayers()
    }

    private fun savePrayers() {

        val todayDate = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(Date())
        val daysOfDate = getDaysDatePage()

        val daysOfYear = _yearPerformed.value!! * 365.25
        val daysOfMonth = _monthPerformed.value!! * 365.25 / 12
        val days = _dayPerformed.value!!
        val performedPrayer = (daysOfYear + daysOfMonth + days).toInt()
        val remainingDays = if (daysOfDate <= performedPrayer) 0 else (daysOfDate - performedPrayer)

        val prayerTimes = context.resources.getStringArray(R.array.PrayerTimesInUzb)
        val progressColors = context.resources.getStringArray(R.array.progress_colors)
        val progressColors20 = context.resources.getStringArray(R.array.progress_colors_20)

        prayerTimes.forEachIndexed { index, s ->
            val prayer = Prayer(
                prayerTimeName = s,
                performedCount = 50,
                remainingCount = remainingDays,
                date = todayDate,
                todayPerformedCount = 0,
                progressColor = progressColors[index],
                progressColor20 = progressColors20[index]
            )

            userUseCases.addPrayer(prayer)
        }
    }

    private fun saveUser() {
        val user = getUser()
        userUseCases.addUser(user)
    }

    private fun getUser(): User {

        return User(
            gender = _gender.value!!,
            birthDate = _birthDate.value ?: "",
            pubertyAge = _pubertyAge.value ?: 9,
            menstrualDays = _menstrualDays.value ?: if (_gender.value == Constants.FEMALE) 6 else 0,
        )
    }

    @SuppressLint("LogNotTimber")
    fun getDaysDatePage(): Int {

        val oldDate = Calendar.getInstance()
        val mYear = year.value!! + pubertyAge.value!!
        val mMonth = month.value!!
        val mDay = day.value!!
        oldDate.set(Calendar.YEAR, mYear)
        oldDate.set(Calendar.MONTH, mMonth)
        oldDate.set(Calendar.DAY_OF_MONTH, mDay)

        val nowDate = Calendar.getInstance()

        val diff = nowDate.timeInMillis - oldDate.timeInMillis
        var days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
        Log.d(TAG, "getDaysAfterDatePage: ${pubertyAge.value!!} and $days")

        if (gender.value == Constants.FEMALE) {
            Log.d(TAG, "getDaysAfterDatePage: ${pubertyAge.value!!} and $days")
            val allMenstrualDays = ((days / 365.25) * 12) * menstrualDays.value!!
            Log.d(TAG, "getDaysAfterDatePage: ${menstrualDays.value!!} and $allMenstrualDays")
            days -= allMenstrualDays.toInt()
        }

        return days
    }

}