package projet.ift604.broomitclient.ui.today

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import projet.ift604.broomitclient.models.Task
import projet.ift604.broomitclient.ApplicationState
import androidx.recyclerview.widget.LinearLayoutManager
import projet.ift604.broomitclient.databinding.FragmentTodayBinding
import java.time.LocalDate

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var scheduledTasks: ArrayList<Task> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.todayClock.text = LocalDate.now().toString()

        scheduledTasks = ApplicationState.instance.getScheduleTasks()

        if (scheduledTasks.size == 0) {
            binding.noTasksText.visibility = View.VISIBLE
        } else {
            binding.tasksRecycler.layoutManager = LinearLayoutManager(context)
            binding.tasksRecycler.adapter = TodayAdapter(scheduledTasks, lifecycleScope)
            binding.noTasksText.visibility = View.GONE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}