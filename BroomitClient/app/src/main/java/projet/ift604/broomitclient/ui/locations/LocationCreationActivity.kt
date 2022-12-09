package projet.ift604.broomitclient.ui.locations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.JsonObject
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.databinding.ActivityCreateLocationBinding
import projet.ift604.broomitclient.models.Location
import projet.ift604.broomitclient.ui.find_location.FindLocationActivity
import java.math.BigDecimal

class LocationCreationActivity : AppCompatActivity() {

    private var _binding: ActivityCreateLocationBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appBar = supportActionBar!!

        appBar.setDisplayHomeAsUpEnabled(true)
        appBar.title = getString(R.string.create_location_title)

        binding.findLocation.setOnClickListener {
            val intent = Intent(this, FindLocationActivity::class.java)
            startActivity(intent)

            // TODO: Déplacer cette logique dans l'activity reliée
            /*
            val handler = CoroutineExceptionHandler { _, err -> err.printStackTrace() }
            lifecycleScope.launch(Dispatchers.IO + handler) {
                val state = ApplicationState.instance
                try {
                    // TODO: Utiliser l'adresse fournie par l'utilisateur
                    val response = state.nominatim("Université de Sherbrooke")
                    // TODO: Afficher tous les résultats obtenus
                    val result = response?.get(0) as JsonObject
                    val displayName = result["display_name"].toString()
                    val latitude = BigDecimal(result.getAsJsonPrimitive("lat").asString)
                    val longitude = BigDecimal(result.getAsJsonPrimitive("lon").asString)

                    binding.notes.setText("$displayName\n$latitude\n$longitude")

                } catch (e: Exception) {
                    // TODO
                    System.out.println(e.message)
                }
                // TODO
            }
            */
        }

        binding.save.setOnClickListener {
            val state = ApplicationState.instance

            val name = binding.locationName.text.toString().trim()
            val notes = binding.notes.text.toString().trim()
            val phoneNum = binding.phoneNumber.text.toString().trim()

            val loc = Location(
                name,
                "addresse bidon", // TEMPORARY
                notes,
                phoneNum,
                Location.Geolocation(0.0,0.0), // TEMPORARY
            )

            lifecycleScope.launch(Dispatchers.IO) {
                state.user.locations.add(loc)
                state.updateUser()
            }

            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}