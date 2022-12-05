package projet.ift604.broomitclient.ui.locations

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        val locations = ApplicationState.getInstance().user.locations

        binding.locationsRecycler.layoutManager = LinearLayoutManager(context)
        binding.locationsRecycler.adapter = LocationsAdapter(locations)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}