package projet.ift604.broomitclient.ui.locations

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.models.Location
import projet.ift604.broomitclient.models.Task
import androidx.recyclerview.widget.RecyclerView

class LocationsAdapter(private val tasks: ArrayList<Location>) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.location_name)
        val phone: TextView = itemView.findViewById(R.id.location_number)
        val address: TextView = itemView.findViewById(R.id.location_address)
        val notes: TextView = itemView.findViewById(R.id.location_notes)
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
        holder.address.text = loc.address
        holder.phone.text = loc.owner_phone_number
    }
}