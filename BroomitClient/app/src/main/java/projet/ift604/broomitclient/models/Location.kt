package projet.ift604.broomitclient.models

import kotlinx.serialization.Serializable

@Serializable
class Location(
    val id: String,
    val name: String,
    val address: String,
    val notes: String,
    val ownerPhoneNumber: String,
    val tasks: ArrayList<Task>,
    val position: Geolocation
) {
    @Serializable
    class Geolocation(val latitude: Double, val longitude: Double);
}