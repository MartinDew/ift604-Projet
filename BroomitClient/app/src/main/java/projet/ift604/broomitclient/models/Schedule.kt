package projet.ift604.broomitclient.models

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Serializable
class Schedule(
    private var due: Instant,
    private var last_done: Instant,
    val every_n: UInt,
    val type: ScheduleType,
) {
    enum class ScheduleType { Daily, Weekly, Yearly }

    fun getDateTime(): LocalDateTime {
        return due.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun isScheduled(): Boolean {
        val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val last = last_done.toLocalDateTime(TimeZone.UTC)
        return now.date > last.date
    }

    fun setDone() {
        last_done = Clock.System.now()

        // Calculate next due instant
        when (type) {
            ScheduleType.Daily -> while (due < Clock.System.now()) due = due.plus(every_n.toInt(), DateTimeUnit.DAY, TimeZone.UTC)
            ScheduleType.Weekly -> while (due < Clock.System.now()) due = due.plus(every_n.toInt(), DateTimeUnit.WEEK, TimeZone.UTC)
            ScheduleType.Yearly -> while (due < Clock.System.now()) due = due.plus(every_n.toInt(), DateTimeUnit.YEAR, TimeZone.UTC)
        }
    }
}