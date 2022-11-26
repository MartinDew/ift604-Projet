package projet.ift604.broomitclient

import projet.ift604.broomitclient.api.UserService
import projet.ift604.broomitclient.models.User
import java.lang.Exception

class ApplicationState {
    var user: User? = null

    // This logs in the server and fetches the user
    fun login(username: String, password: String) {
        val service = UserService.getInstance()

        // fetch user with username and password
        val resp = service.login(UserService.LoginRequest(username, password)).execute()

        if (resp.code() == 200) {
            user = resp.body()
        } else {
            throw Exception(resp.errorBody().toString())
        }
    }

    companion object {
        private var instance: ApplicationState? = null

        fun getInstance(): ApplicationState {
            if (instance == null)
                instance = ApplicationState()

            return instance as ApplicationState
        }
    }
}