package projet.ift604.broomitclient.ui.today

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import projet.ift604.broomitclient.models.Task
import projet.ift604.broomitclient.ApplicationState
import androidx.recyclerview.widget.LinearLayoutManager
import projet.ift604.broomitclient.databinding.FragmentTodayBinding

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val scheduledTasks: ArrayList<Task> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        scheduledTasks.clear()
        scheduledTasks.addAll(ApplicationState.instance.getScheduleTasks())

        if (scheduledTasks.size == 0) {
            binding.noTasksText.visibility = View.VISIBLE
        } else {
            binding.tasksRecycler.layoutManager = LinearLayoutManager(context)
            binding.tasksRecycler.adapter = TodayAdapter(scheduledTasks)
            binding.noTasksText.visibility = View.GONE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}