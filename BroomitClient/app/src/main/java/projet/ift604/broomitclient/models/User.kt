package projet.ift604.broomitclient.models

import kotlinx.serialization.Serializable

@Serializable
class User(
    val id: String,
    val username: String,
    val password: String,
    val email: String,
    val locations: ArrayList<Location>
) {
}