package projet.ift604.broomitclient.ui.locations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.databinding.ActivityCreateLocationBinding
import projet.ift604.broomitclient.models.Location

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
            val intent = Intent(this, MapsFragment::class.java)
            startActivity(intent)
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