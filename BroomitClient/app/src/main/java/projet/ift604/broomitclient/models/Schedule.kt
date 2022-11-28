package projet.ift604.broomitclient.models

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@Serializable
class Schedule(
    val startDateTime: LocalDateTime,
    val everyN: UInt,
    val type: ScheduleType,
    val days: ArrayList<UInt>
) {
    enum class ScheduleType { Daily, Weekly, Yearly }

    fun getDate(): LocalDate {
        return startDateTime.date
    }

    fun getTime(): LocalTime {
        return startDateTime.time
    }

    fun isScheduled(date: LocalDate): Boolean {
        return when (type) {
            ScheduleType.Daily -> (date.toEpochDays() - getDate().toEpochDays()).toUInt() % everyN == 0U
            ScheduleType.Yearly -> (date.year - getDate().year).toUInt() % everyN == 0U && date.dayOfMonth == getDate().dayOfMonth && date.month == getDate().month
            ScheduleType.Weekly -> isScheduledWeek(date)
        }
    }

    fun isScheduledWeek(date: LocalDate): Boolean {
        val currentDayOfWeek = date.dayOfWeek.value.toUInt()

        // first day of the current week
        val currentWeekNumber = date.toEpochDays().toUInt() - currentDayOfWeek / 7U
        val startWeekNumber = getDate().toEpochDays().toUInt() / 7U

        val weekDiff = currentWeekNumber - startWeekNumber

        return days.any{x -> x == currentDayOfWeek} && (weekDiff % everyN) == 0U
    }
}