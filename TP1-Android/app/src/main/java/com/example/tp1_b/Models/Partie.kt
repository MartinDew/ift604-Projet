package com.example.tp1_b.Models

class Partie(
    var joueur1: Joueur? = null,
    var joueur2: Joueur? = null,
    val terrain: String = "",
    val tournoi: String = "",
    val heure_debut: String = "00h00",
    var pointage: Pointage? = null,
    var temps_partie: ULong = 0U,
    var joueur_au_service: Joueur? = null,
    var vitesse_dernier_service: UInt = 0U,
    var nombre_coup_dernier_echange: UInt = 0U,
    var constestation: Array<UInt> = arrayOf(),
    var tick_debut: ULong = 0U
) {}