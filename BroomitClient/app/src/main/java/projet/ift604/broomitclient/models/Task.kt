package projet.ift604.broomitclient.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
class Task(
    val name: String,
    val notes: String,
    val schedule: Schedule?,
    val notify_on_due: Boolean
) {
    fun setDone() {
        schedule?.setDone()
    }

    fun isScheduled(): Boolean {
        return schedule?.isScheduled() ?: false
    }
}