package projet.ift604.broomitclient

import projet.ift604.broomitclient.api.UserService
import projet.ift604.broomitclient.models.User
import java.lang.Exception

class ApplicationState {
    class HttpException(val code: Int, val msg: String = "") : Throwable()

    var loggedIn: Boolean = false
    var user: User? = null

    fun getUser(userId: String) {
        val service = UserService.getInstance()

        val resp = service.getUser(userId).execute()
        val body = resp.body()

        if (resp.code() == 200 && body != null) {
            user = body
        } else {
            val err = resp.errorBody();
            if (err != null)
                throw HttpException(resp.code(), err.string())
            throw HttpException(resp.code())
        }
    }

    // This logs in the server and fetches the user
    fun login(loginReq: UserService.LoginRequest) {
        val service = UserService.getInstance()

        // fetch user with username and password
        val resp = service.login(loginReq).execute()
        val body = resp.body()

        if (resp.code() == 200 && body != null) {
            getUser(body)
            loggedIn = true
        } else {
            val err = resp.errorBody();
            if (err != null)
                throw HttpException(resp.code(), err.string())
            throw HttpException(resp.code())
        }
    }

    fun create(createReq: UserService.CreateRequest) {
        val service = UserService.getInstance()

        // fetch user with username and password
        val resp = service.create(createReq).execute()
        val body = resp.body()

        if (resp.code() == 200 && body != null) {
            getUser(body)
            loggedIn = true
        } else {
            val err = resp.errorBody();
            if (err != null)
                throw HttpException(resp.code(), err.string())
            throw HttpException(resp.code())
        }
    }

    // Refreshes the user loaded with the api one
    fun refreshUser() {
        if (user == null) throw Exception("User not loaded")

        getUser(user!!.id)
    }

    // Updates the user on the api with the current loaded user
    fun updateUser() {
        if (user == null) throw Exception("User not loaded")

        val service = UserService.getInstance()

        val resp = service.updateUser(user!!.id, user!!).execute()

        if (resp.code() != 200) {
            val err = resp.errorBody();
            if (err != null)
                throw HttpException(resp.code(), err.string())
            throw HttpException(resp.code())
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