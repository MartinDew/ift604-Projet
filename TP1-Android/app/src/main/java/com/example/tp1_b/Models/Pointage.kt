package com.example.tp1_b.Models

import kotlinx.serialization.Serializable

@Serializable
class Pointage(
    var manches: ArrayList<Int> = ArrayList(),
    var jeu: ArrayList<ArrayList<Int>> = ArrayList(),
    var echange: ArrayList<Int> = ArrayList(),
    var final: Boolean = false,
    var parent: Partie? = null,
)