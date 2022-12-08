package projet.ift604.broomitclient.ui.locations

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.databinding.FragmentLocationsBinding


class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.locationsRecycler.layoutManager = LinearLayoutManager(context)

        binding.addLocation.setOnClickListener {
            val intent = Intent(context, LocationCreationActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val locations = ApplicationState.instance.user.locations
        binding.locationsRecycler.adapter = LocationsAdapter(locations, lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}