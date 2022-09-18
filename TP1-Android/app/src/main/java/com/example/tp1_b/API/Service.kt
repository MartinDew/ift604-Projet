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

    @JvmName("GetArray")
    protected inline fun <reified T> getRequest(
        conn: HttpURLConnection,
        crossinline dataCallback: (ArrayList<T>) -> Unit,
        crossinline errCallback: (Exception) -> Unit)
    {
        runGetRequest(conn, dataCallback, errCallback) { Klaxon().parseArray<T>(it) as ArrayList<T> }
    }

    @JvmName("Get")
    protected inline fun <reified T> getRequest(
        conn: HttpURLConnection,
        crossinline dataCallback: (T) -> Unit,
        crossinline errCallback: (Exception) -> Unit)
    {
        runGetRequest(conn, dataCallback, errCallback) { Klaxon().parse<T>(it) as T }
    }

    protected inline fun <reified T> runGetRequest(
        conn: HttpURLConnection,
        crossinline dataCallback: (T) -> Unit,
        crossinline errCallback: (Exception) -> Unit,
        crossinline parseFun: (String) -> T
    )
    {
        thread(start = true) {
            try {
                conn.requestMethod = "GET"

                val statusCode = conn.responseCode
                if (statusCode != 200) {
                    val errMsg = conn.errorStream.bufferedReader().readText()
                    throw Exception(errMsg)
                }

                val text = conn.inputStream.bufferedReader().readText()
                dataCallback(parseFun(text))
            } catch (e: Exception) {
                errCallback(e)
            }
        }
    }
}