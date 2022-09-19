package com.example.tp1_b.Models

class Partie(
    val id_partie: Int = -1,
    val joueur1: Joueur = Joueur(),
    val joueur2: Joueur = Joueur(),
    val terrain: String = "<no terrain>",
    val tournoi: String = "<no tournament>",
    val heure_debut: String = "<no start time>",
    val pointage: Pointage? = null,
    val temps_partie: Int = -1,
    val joueur_au_service: Joueur? = null,
    val vitesse_dernier_service: Int = -1,
    val nombre_coup_dernier_echange: Int = -1,
    val constestation: ArrayList<Int> = ArrayList(),
)