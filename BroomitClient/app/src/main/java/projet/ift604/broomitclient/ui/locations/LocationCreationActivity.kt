package projet.ift604.broomitclient.ui.locations

import android.os.Bundle
import android.view.MenuItem
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

    // Fix to ensure 'Back' from 'LocationCreationActivity' return here
    // https://stackoverflow.com/questions/31491093/how-to-go-back-to-previous-fragment-from-activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}