package com.example.tp1_b.Models

class Partie(
    val id_partie: Int = -1,
    val joueur1: Joueur? = null,
    val joueur2: Joueur? = null,
    val terrain: String = "",
    val tournoi: String = "",
    val heure_debut: String = "00h00",
    val pointage: Pointage? = null,
    val temps_partie: Int = -1,
    val joueur_au_service: Joueur? = null,
    val vitesse_dernier_service: Int = -1,
    val nombre_coup_dernier_echange: Int = -1,
    val constestation: ArrayList<Int> = ArrayList(),
)