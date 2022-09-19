package com.example.tp1_b.Models

import kotlinx.serialization.Serializable

@Serializable
class Joueur(
    val prenom: String = "<no first name>",
    val nom: String = "<no last name>",
    val age: Int = -1,
    val rang: Int = -1,
    val pays: String = "<no country>",
)