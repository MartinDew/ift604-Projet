package com.example.tp1_b.API

import android.content.Context
import android.graphics.Point
import com.example.tp1_b.Models.Partie
import com.beust.klaxon.Klaxon
import com.example.tp1_b.Models.Joueur
import com.example.tp1_b.Models.Pointage
import kotlinx.coroutines.coroutineScope
import java.net.HttpURLConnection
import java.net.URL

class PartieService(context: Context) : Service(context) {
    fun commonSetup(conn: HttpURLConnection) {

    }

    fun getParties(dataCallback: (Array<Partie>) -> Unit, errCallback: (Exception) -> Unit) {
        Get(
            URL(baseUrl + "parties"),
            ::commonSetup,
            dataCallback,
            errCallback
        )
    }
}