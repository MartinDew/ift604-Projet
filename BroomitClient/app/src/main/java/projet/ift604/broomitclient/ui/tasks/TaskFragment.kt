package projet.ift604.broomitclient.ui.tasks

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.databinding.ActivityCreateTaskBinding

class TaskFragment : Fragment() {
    private var _binding: ActivityCreateTaskBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityCreateTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root
//
//        binding.locationsRecycler.layoutManager = LinearLayoutManager(context)
//
//        binding.addLocation.setOnClickListener {
//            val intent = Intent(context, TaskCreationActivity::class.java)
//            startActivity(intent)
//        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val tasks = ApplicationState.instance.user.locations[id].tasks
        //binding.taskRecycler.adapter = TasksAdapter(tasks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}