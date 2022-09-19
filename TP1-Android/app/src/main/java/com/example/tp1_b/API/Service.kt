package com.example.tp1_b.API

import android.content.Context
import com.example.tp1_b.R
import java.net.HttpURLConnection
import kotlin.concurrent.thread
import kotlinx.serialization.*
import kotlinx.serialization.json.*

open class Service(context: Context) {
    protected val baseUrl = context.getString(R.string.base_url)

    protected inline fun <reified T> getRequest(
        conn: HttpURLConnection,
        crossinline dataCallback: (T) -> Unit,
        crossinline errCallback: (Exception) -> Unit)
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
                dataCallback(Json.decodeFromString(text))
            } catch (e: Exception) {
                errCallback(e)
            }
        }
    }
}