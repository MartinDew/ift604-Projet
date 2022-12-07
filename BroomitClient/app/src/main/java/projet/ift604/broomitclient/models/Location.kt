package projet.ift604.broomitclient.models

import kotlinx.serialization.Serializable

@Serializable
class Location(
    val name: String,
    val address: String,
    val notes: String,
    val owner_phone_number: String,
    val position: Geolocation,
    val tasks: ArrayList<Task> = ArrayList()
) {
    @Serializable
    class Geolocation(val latitude: Double, val longitude: Double);
}