package projet.ift604.broomitclient.api

import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import projet.ift604.broomitclient.API_URL
import projet.ift604.broomitclient.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface UserService {

    // Password will be a SHA256 of the given password
    @Serializable
    class LoginRequest(val username: String, val password: String)

    @Serializable
    class CreateRequest(val username: String, val password: String, val email: String)

    @Headers("Connection: close")
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<String>

    @POST("user")
    fun create(@Body createRequest: CreateRequest): Call<String>

    @GET("user/{userId}")
    fun getUser(@Path("userId") userId: String): Call<User>

    @PUT("user/{userId}")
    fun updateUser(@Path("userId") userId: String, @Body user: User): Call<Unit>

    companion object {
        fun getInstance(): UserService {
            val client = OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(UserService::class.java)
        }
    }
}