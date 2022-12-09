package projet.ift604.broomitclient.api

import com.google.gson.JsonArray
import okhttp3.OkHttpClient
import projet.ift604.broomitclient.NOMINATIM_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface NominatimService {

    @GET("search")
    @Headers("Content-Type: application/json")
    // Chaîne de requête de forme libre à rechercher (gauche à droite ou droite à gauche en cas d'échec).
    fun getSearch(@Query("q") query: String, @Query("format") format: String = "json"): Call<JsonArray>

    companion object {
        fun getInstance(): NominatimService {
            val client = OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(NOMINATIM_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(NominatimService::class.java)
        }
    }
}