package projet.ift604.broomitclient.api

import kotlinx.serialization.Serializable
import projet.ift604.broomitclient.API_URL
import projet.ift604.broomitclient.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @Serializable
    class LoginRequest(val username: String, val password: String)

    @GET("user")
    fun login(@Body loginRequest: LoginRequest): Call<User>

    companion object {
        fun getInstance(): UserService {
            val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .build()

            return retrofit.create(UserService::class.java)
        }
    }
}