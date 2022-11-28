package projet.ift604.broomitclient.ui.today

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.databinding.FragmentTodayBinding
import projet.ift604.broomitclient.models.Task

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val scheduledTasks: ArrayList<Task> = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        scheduledTasks.clear()
        scheduledTasks.addAll(ApplicationState.getInstance().getScheduleTasks())

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