package com.example.tp1_b.ui.gallery

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.LayoutInflaterFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_b.Models.Partie
import com.example.tp1_b.R

class PartiesAdapter(private val context: Context, private val parties: List<Partie>) : RecyclerView.Adapter<PartiesAdapter.PartiesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val contactView = inflater.inflate(R.layout.partie_item_view, parent, false)

        return PartiesViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: PartiesViewHolder, position: Int) {
        val partie = parties[position]
        val joueurFmt = context.resources.getString(R.string.partie_item_view_nom_joueur)

        holder.joueur1View.text = String.format(joueurFmt, partie.joueur1.prenom, partie.joueur1.nom)
        holder.joueur2View.text = String.format(joueurFmt, partie.joueur2.prenom, partie.joueur2.nom)

        val playerThatServes = if (partie.joueur_au_service == 0) holder.joueur1View else holder.joueur2View
        playerThatServes.paintFlags = playerThatServes.paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)

        val score = partie.pointage
        val scoreFmt = context.resources.getString(R.string.partie_item_view_score)
        holder.joueur1ScoreView.text = String.format(scoreFmt, score.manches[0], score.jeu.last()[0], score.echange[0])
        holder.joueur2ScoreView.text = String.format(scoreFmt, score.manches[1], score.jeu.last()[1], score.echange[1])
    }

    override fun getItemCount(): Int {
        return parties.size
    }

    class PartiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // declare member controls
        val joueur1View: TextView = itemView.findViewById<TextView>(R.id.partie_item_view_name_joueur_1)
        val joueur1ScoreView: TextView = itemView.findViewById<TextView>(R.id.partie_item_view_score_1)

        val joueur2View: TextView = itemView.findViewById<TextView>(R.id.partie_item_view_name_joueur_2)
        val joueur2ScoreView: TextView = itemView.findViewById<TextView>(R.id.partie_item_view_score_2)
    }
}