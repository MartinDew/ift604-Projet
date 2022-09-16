package com.example.tp1_b.API

import android.content.Context
import com.beust.klaxon.Klaxon
import com.example.tp1_b.Models.Partie
import com.example.tp1_b.R
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

open class Service(context: Context) {
    protected val baseUrl = context.getString(R.string.base_url)

    protected inline fun <reified T> Get(
        url: URL,
        crossinline setup: (HttpURLConnection) -> Unit,
        crossinline dataCallback: (T) -> Unit,
        crossinline errCallback: (Exception) -> Unit)
    {
        thread(start = true) {
            try {
                var parties: T?

                val conn = url.openConnection() as HttpURLConnection
                setup(conn)

                with(conn) {
                    parties = Klaxon().parse(inputStream.bufferedReader().readText())
                }

                if (parties == null) {
                    errCallback(Exception("Couldn't parse JSON"))
                } else {
                    dataCallback(parties as T)
                }
            } catch (e: Exception) {
                errCallback(e)
            }
        }
    }
}