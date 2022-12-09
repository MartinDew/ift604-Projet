package projet.ift604.broomitclient

import android.location.Location
import android.telephony.TelephonyCallback.CellLocationListener
import com.google.gson.JsonArray
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import projet.ift604.broomitclient.api.NominatimService
import projet.ift604.broomitclient.api.UserService
import projet.ift604.broomitclient.models.Task
import projet.ift604.broomitclient.models.User
import retrofit2.Call
import java.lang.Exception
import java.lang.Math.pow
import java.lang.Math.sqrt

class ApplicationState {
    class HttpException(val code: Int, val msg: String = "") : Throwable()

    val loggedIn: Boolean get() = _user != null

    var user: User
        get() = _user!!
        set(value) { _user = value }

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


    // Se connecter au serveur et récupérer les informations de l'utilisateur
    @Throws(HttpException::class)
    fun login(loginReq: UserService.LoginRequest) {
        val service = UserService.getInstance()
        val userId = callAPI(service.login(loginReq))

        getUser(userId!!)
    }

    fun logout() {
        _user = null
    }

    @Throws(HttpException::class)
    fun create(createReq: UserService.CreateRequest) {
        val service = UserService.getInstance()

        // Créer l'utilisateur avec les informations fournies
        val userId = callAPI(service.create(createReq))

        getUser(userId!!)
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

    fun getLocationInProximity(loc: Location, range: Double): ArrayList<projet.ift604.broomitclient.models.Location> {
        if (!loggedIn) throw Exception("User not loaded")

        val locs = ArrayList<projet.ift604.broomitclient.models.Location>()

        user.locations.forEach {

            val LocLong = it.position.longitude
            val LocLat = it.position.latitude

            if (sqrt(pow((LocLong - loc.longitude), 2.0) + pow(LocLat - loc.latitude, 2.0)) < range)
                locs.add(it)
        }

        return locs
    }

    // Rafraîchir l'utilisateur chargé avec celui de l'API
    @Throws(HttpException::class)
    fun refreshUser() {
        if (!loggedIn) throw Exception("User not loaded")

        getUser(user.id)
    }

    // Mettre à jour l'utilisateur sur l'API avec l'utilisateur chargé actuel
    @Throws(HttpException::class)
    fun updateUser(passwordModified: Boolean = false) {
        if (!loggedIn) throw Exception("User not loaded")

        val service = UserService.getInstance()

        if (!passwordModified)
            user.password = ""

        callAPI(service.updateUser(user.id, user))
    }

    // Faire une requête à l'API externe Nominatim
    // https://nominatim.openstreetmap.org/ui/search.html
    @Throws(HttpException::class)
    fun nominatim(query: String): JsonArray? {
        if (!loggedIn) throw Exception("User not loaded")

        val service = NominatimService.getInstance()

        return callAPI(service.getSearch(query))
    }

    companion object {
        private var _instance: ApplicationState? = null

        val instance: ApplicationState
            get() {
            if (_instance == null)
                _instance = ApplicationState()

            return _instance as ApplicationState
        }
    }
}