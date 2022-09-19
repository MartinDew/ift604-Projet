package com.example.tp1_b.API

import android.content.Context
import com.example.tp1_b.Models.Partie
import java.net.HttpURLConnection
import java.net.URL

class PartieService(context: Context) : Service(context) {
    fun getParties(dataCallback: (ArrayList<Partie>) -> Unit, errCallback: (Exception) -> Unit) {
        getRequest(
            URL(baseUrl + "parties").openConnection() as HttpURLConnection,
            dataCallback,
            errCallback
        )
    }

    fun getPartie(idPartie: Int, dataCallback: (Partie) -> Unit, errCallback: (Exception) -> Unit) {
        getRequest(
            URL(baseUrl + "parties/" + idPartie).openConnection() as HttpURLConnection,
            dataCallback,
            errCallback
        )
    }
}