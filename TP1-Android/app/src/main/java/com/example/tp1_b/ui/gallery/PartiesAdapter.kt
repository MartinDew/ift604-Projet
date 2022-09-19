package com.example.tp1_b.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.LayoutInflaterFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_b.Models.Partie
import com.example.tp1_b.R

class PartiesAdapter(private val parties: List<Partie>) : RecyclerView.Adapter<PartiesAdapter.PartiesViewHolder>() {
    var joueur_format: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        joueur_format = parent.context.resources.getString(R.string.partie_item_view_nom_joueur)
        val contactView = inflater.inflate(R.layout.partie_item_view, parent, false)

        return PartiesViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: PartiesViewHolder, position: Int) {
        val partie = parties[position]

        holder.joueur1View.text = String.format(joueur_format, partie.joueur1.prenom, partie.joueur1.nom)
        holder.joueur2View.text = String.format(joueur_format, partie.joueur2.prenom, partie.joueur2.nom)
    }

    override fun getItemCount(): Int {
        return parties.size
    }

    class PartiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // declare member controls
        val joueur1View = itemView.findViewById<TextView>(R.id.partie_item_view_name_joueur_1)
        val joueur2View = itemView.findViewById<TextView>(R.id.partie_item_view_name_joueur_2)
    }
}