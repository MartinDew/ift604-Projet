package projet.ift604.broomitclient.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Transient

@Serializable
class Task(
    val name: String,
    val notes: String,
    val schedule: Schedule?,
    val notify_on_due: Boolean,

    @Transient var parent: Location? = null
) {
    fun setDone() {
        schedule?.setDone()
    }

    fun isScheduled(): Boolean {
        return schedule?.isScheduled() ?: false
    }
}