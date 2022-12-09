package projet.ift604.broomitclient.ui.today

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.models.Task

class TodayAdapter(private val tasks: ArrayList<Task>, val scope: LifecycleCoroutineScope) : RecyclerView.Adapter<TodayAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.task_name)
        val time: TextView = itemView.findViewById(R.id.task_time)
        val notes: TextView = itemView.findViewById(R.id.task_notes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        holder.itemView.setOnLongClickListener {
            scope.launch(Dispatchers.IO) {
                val state = ApplicationState.instance
                task.setDone()
                task.parent = null
                state.updateUser()
            }

            tasks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount - position)

            true
        }

        holder.name.text = "${task.parent?.name} - ${task.name}"
        holder.notes.text = task.notes

        val time = task.schedule?.getDateTime()

        if (time != null)
            holder.time.text = String.format("%d:%d", time.hour, time.minute)
    }
}