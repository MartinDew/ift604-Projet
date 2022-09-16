package com.example.tp1_b.Models

class Pointage(
    var manches: Array<UInt>,
    var jeu: Array<Array<UInt>>,
    var echange: Array<UInt>,
    var final: Boolean,
    var parent: Partie,
) {}