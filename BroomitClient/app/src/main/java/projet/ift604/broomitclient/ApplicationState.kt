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
import retrofit2.Call
import java.lang.Exception

class ApplicationState {
    class HttpException(val code: Int, val msg: String = "") : Throwable()

    var loggedIn: Boolean = false

    val user get() = _user!!
    var _user: User? = null

    @Throws(HttpException::class)
    fun <T> callAPI(call: Call<T>): T? {
        val resp = call.execute()
        val body = resp.body()

        if (resp.code() in 200..299) {
            if (body != null)
                return body
        } else {
            val err = resp.errorBody()
            if (err != null)
                throw HttpException(resp.code(), err.string())
            throw HttpException(resp.code())
        }
        return null
    }

    @Throws(HttpException::class)
    fun getUser(userId: String) {
        val service = UserService.getInstance()

        _user = callAPI(service.getUser(userId))
    }


    // This logs in the server and fetches the user
    @Throws(HttpException::class)
    fun login(loginReq: UserService.LoginRequest) {
        val service = UserService.getInstance()
        val userId = callAPI(service.login(loginReq))

        getUser(userId!!)
        loggedIn = true
    }

    fun logout() {
        loggedIn = false;
        _user = null;
    }

    @Throws(HttpException::class)
    fun create(createReq: UserService.CreateRequest) {
        val service = UserService.getInstance()

        // fetch user with username and password
        val userId = callAPI(service.create(createReq))

        getUser(userId!!)
        loggedIn = true
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
    @Throws(HttpException::class)
    fun refreshUser() {
        if (!loggedIn) throw Exception("User not loaded")

        getUser(user.id)
    }

    // Updates the user on the api with the current loaded user
    @Throws(HttpException::class)
    fun updateUser() {
        if (!loggedIn) throw Exception("User not loaded")

        val service = UserService.getInstance()

        callAPI(service.updateUser(user.id, user))
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