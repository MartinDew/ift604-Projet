package projet.ift604.broomitclient.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
class Task(
    val id: String,
    val name: String,
    val notes: String,
    val schedule: Schedule?,
    val notify_on_due: Boolean
) {
    fun isScheduled(date: LocalDate): Boolean {
        return schedule?.isScheduled(date) ?: false
    }
}