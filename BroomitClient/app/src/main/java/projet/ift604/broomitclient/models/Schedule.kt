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
        return due.toLocalDateTime(TimeZone.UTC)
    }

    fun isScheduled(): Boolean {
        return Clock.System.now() > last_done;
    }

    fun setDone() {
        last_done = Clock.System.now()

        // Calculate next due instant
        when (type) {
            ScheduleType.Daily -> due.plus(every_n.toInt(), DateTimeUnit.DAY, TimeZone.UTC)
            ScheduleType.Weekly -> due.plus(every_n.toInt(), DateTimeUnit.WEEK, TimeZone.UTC)
            ScheduleType.Yearly -> due.plus(every_n.toInt(), DateTimeUnit.YEAR, TimeZone.UTC)
        }
    }
}