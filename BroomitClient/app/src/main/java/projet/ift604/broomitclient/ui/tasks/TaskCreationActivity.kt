package projet.ift604.broomitclient.ui.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.databinding.ActivityCreateTaskBinding
import projet.ift604.broomitclient.models.Schedule
import projet.ift604.broomitclient.models.Task

class TaskCreationActivity : AppCompatActivity() {
    private var _binding: ActivityCreateTaskBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appBar = supportActionBar!!

        appBar.setDisplayHomeAsUpEnabled(true)
        appBar.title = getString(R.string.create_task_title)
        val id = intent.getIntExtra("LocationId", -1)
        binding.save.setOnClickListener {
            val state = ApplicationState.instance

            val name = binding.taskName.text.toString().trim()
            val notes = binding.notes.text.toString().trim()
            val currentMoment: Instant = Clock.System.now()
            val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
            val task = Task(
                id = "",
                name = name,
                notes = notes,
                schedule = Schedule(datetimeInUtc, 2u,Schedule.ScheduleType.Daily,ArrayList()),
                notify_on_due = false,
            )
            lifecycleScope.launch(Dispatchers.IO) {
                if(id != -1) {
                    state.user.locations[id].tasks.add(task)
                    state.updateUser()
                }
            }

            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}