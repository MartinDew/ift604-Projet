package projet.ift604.broomitclient.ui.find_location

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.databinding.ActivityFindLocationBinding

class FindLocationActivity : AppCompatActivity(), OnMapReadyCallback  {
    private lateinit var binding: ActivityFindLocationBinding

    private var displayName: String = ""

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private var gMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_location)

        supportActionBar?.title = getString(R.string.prompt_find_location)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val submit: Button = findViewById(R.id.submit)
        submit.setOnClickListener {
            val handler = CoroutineExceptionHandler { _, err -> err.printStackTrace() }
            lifecycleScope.launch(Dispatchers.IO + handler) {
                val state = ApplicationState.instance
                try {
                    val query: String = findViewById<EditText?>(R.id.query).text.toString()
                    val response = state.nominatim(query)
                    // TODO: Afficher tous les résultats obtenus
                    val result = response?.get(0) as JsonObject

                    displayName = result["display_name"].toString()
                    latitude = (result.getAsJsonPrimitive("lat").asString).toDouble()
                    longitude = (result.getAsJsonPrimitive("lon").asString).toDouble()

                    runOnUiThread {
                        // Effacer la position précédente
                        gMap?.clear()

                        val latLng = LatLng(latitude, longitude)

                        // Créer un nouveau marqueur
                        val markerOptions = MarkerOptions()
                        markerOptions.position(latLng)

                        // Donner un titre au marqueur avec la latitude/longitude obtenue
                        markerOptions.title("(" + latLng.latitude.toString() + "; " + latLng.longitude.toString() + ")")

                        // Ajouter le marqueur à la carte
                        gMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                        gMap?.addMarker(markerOptions)
                    }
                } catch (e: Exception) {
                    // TODO
                    System.out.println(e.message)
                }
                // TODO
            }
        }
    }

override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap

        // Afficher les contrôles pour zoomer
        gMap!!.uiSettings.isZoomControlsEnabled = true
    }
}
