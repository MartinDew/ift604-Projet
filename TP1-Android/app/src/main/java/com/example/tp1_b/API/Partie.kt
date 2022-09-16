package com.example.tp1_b.API

import android.graphics.Point
import com.example.tp1_b.Models.Joueur
import com.example.tp1_b.Models.Partie
import com.example.tp1_b.Models.Pointage
import java.net.HttpURLConnection
import java.net.URL

class Partie {
    fun getParties(): Array<Partie> {
        val url = URL("http://www.google.com/")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET
            inputStream.bufferedReader().use {
                it.lines().forEach { line ->
                    println(line)
                }
            }
        }

        val j1 = Joueur("Alex", "LB", 22U, 1U, "Canada")
        val j2 = Joueur("Alex", "LB", 22U, 2U, "Canada")

        val partie = Partie()
        with(partie) {
            joueur1 = j1
            joueur2 = j2
            pointage = Pointage(arrayOf(), arrayOf(), arrayOf(), true, this)
        }

        return arrayOf(partie)
    }
}