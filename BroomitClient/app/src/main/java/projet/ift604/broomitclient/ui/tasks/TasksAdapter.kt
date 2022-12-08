package projet.ift604.broomitclient.ui.tasks

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.models.Location
import projet.ift604.broomitclient.models.Task
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private val tasks: ArrayList<Task>) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.location_name)
        val notes: TextView = itemView.findViewById(R.id.location_number)
        val schedule: TextView = itemView.findViewById(R.id.location_address)
        val recurence: TextView = itemView.findViewById(R.id.location_notes)
        val recurenceType: TextView = itemView.findViewById(R.id.location_notes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val loc = tasks[position]

        holder.name.text = loc.name
        holder.notes.text = loc.notes
        //holder.schedule.text = loc.schedule
        //holder.phone.text = loc.owner_phone_number
    }
}