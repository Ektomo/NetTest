package com.gorbunov.nettest.gate

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.ConnectException
import java.util.concurrent.TimeUnit

class Gate {


    private val httpClient: OkHttpClient
    @OptIn(ExperimentalSerializationApi::class)
    val format = Json {
        prettyPrint = true//Удобно печатает в несколько строчек
        ignoreUnknownKeys = true// Неизвестные значение
        coerceInputValues = true// Позволяет кодировать в параметрах null
        explicitNulls = true// Позволяет декодировать в параметрах null
    }



    init {
        val b = OkHttpClient.Builder()

            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .writeTimeout(15000, TimeUnit.MILLISECONDS)
            .readTimeout(15000, TimeUnit.MILLISECONDS)
        httpClient = b.build()
    }




    fun makeRequest(
        url: String
    ): NetTestResponse? {
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        val r = httpClient.newCall(request).execute()

        return if (r.isSuccessful) {
            val response = r.body()?.string()
            if (response != null){
                val a = format.decodeFromString<NetTestResponse>(response)
                a
            }else{
                null
            }
        } else {
            throw ConnectException("Ошибка запроса ${r.body()}")
        }
    }


    companion object {
        var gate: Gate? = null
        fun getInstance(): Gate {
            if (gate == null) {
                gate = Gate()
            }
            return gate!!
        }
    }
}