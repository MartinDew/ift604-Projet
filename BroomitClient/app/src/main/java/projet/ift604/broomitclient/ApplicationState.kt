package projet.ift604.broomitclient

import android.telephony.TelephonyCallback.CellLocationListener
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import projet.ift604.broomitclient.api.UserService
import projet.ift604.broomitclient.models.Task
import projet.ift604.broomitclient.models.User
import java.lang.Exception

class ApplicationState {
    class HttpException(val code: Int, val msg: String = "") : Throwable()

    var loggedIn: Boolean = false

    var userMutex: Mutex = Mutex()
    val user get() = _user!!
    var _user: User? = null

    fun getUser(userId: String) {
        val service = UserService.getInstance()

        val resp = service.getUser(userId).execute()
        val body = resp.body()

        if (resp.code() == 200 && body != null) {
            _user = body
        } else {
            val err = resp.errorBody();
            if (err != null)
                throw HttpException(resp.code(), err.string())
            throw HttpException(resp.code())
        }
    }

    fun updateUser(user: User) {
        val service = UserService.getInstance()

        val resp = service.updateUser(user.id, user).execute()

        if (resp.code() == 204) {
            // Replace with new data
            _user = user
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

    fun logout() {
        loggedIn = false;
        _user = null;
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

    fun getScheduleTasks(): ArrayList<Task> {
        if (!loggedIn) throw Exception("User not loaded")

        val tasks = ArrayList<Task>()
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        for (loc in user.locations) {
            for (task in loc.tasks) {
                if (task.isScheduled(today)) {
                    tasks.add(task)
                }
            }
        }

        return tasks
    }

    // Refreshes the user loaded with the api one
    fun refreshUser() {
        if (!loggedIn) throw Exception("User not loaded")

        getUser(user.id)
    }

    // Updates the user on the api with the current loaded user
    fun updateUser() {
        if (!loggedIn) throw Exception("User not loaded")

        val service = UserService.getInstance()

        val resp = service.updateUser(user.id, user).execute()

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