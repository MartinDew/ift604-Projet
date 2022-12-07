package projet.ift604.broomitclient.models

import kotlinx.serialization.Serializable

@Serializable
class User(
    var id: String,
    var username: String,
    var password: String,
    var email: String,
    var locations: ArrayList<Location>
) {
}