package com.petsvote.core.ext

import androidx.fragment.app.Fragment
import com.petsvote.core.R
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

fun Fragment.getMonthOnYear(value: String): String {
    if(value.isEmpty()) return ""
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var convertedDate = LocalDate.parse(value, formatter)
    var dateNow = LocalDate.now()
    val calendar: Calendar = GregorianCalendar.getInstance()
    val calendarOld: Calendar = GregorianCalendar.getInstance()
    calendarOld.set(Calendar.YEAR, -convertedDate.year)
    calendarOld.set(Calendar.MONTH, -convertedDate.month.value)
    calendarOld.set(Calendar.DAY_OF_MONTH, -convertedDate.dayOfMonth)

    val p: Period = Period.between(convertedDate, dateNow)
    var diff = p.years * 12 + p.months
    return if (diff < 12) "$diff ${this.getString(R.string.month1)}"
    else "${diff / 12}"
}
