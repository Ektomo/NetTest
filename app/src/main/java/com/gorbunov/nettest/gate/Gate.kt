package com.gorbunov.nettest.gate

import com.gorbunov.nettest.model.NetTestResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.ConnectException
import java.util.concurrent.TimeUnit


/**
 * Класс для взаимодействия с сетью
 *  объявлен приватный конструктор, чтобы не создавать новый экземпляр каждый раз
 */
class Gate private constructor(){

    /**
     * Основной клиент для общения с сетью с гибкой настройкой куки, аутенфикации и т.д.
     */
    private val httpClient: OkHttpClient

    /**
     * Экземпляр класса для работы с сериализацией
     */
    @OptIn(ExperimentalSerializationApi::class)
    val format = Json {
        prettyPrint = true//Удобно печатает в несколько строчек
        ignoreUnknownKeys = true// Неизвестные значение
        coerceInputValues = true// Позволяет кодировать в параметрах null
        explicitNulls = true// Позволяет декодировать в параметрах null
    }


    /**
     * Настройка клиента, здесь можно добавить различные настройки в цепочке вызовов
     */
    init {
        val b = OkHttpClient.Builder()
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .writeTimeout(15000, TimeUnit.MILLISECONDS)
            .readTimeout(15000, TimeUnit.MILLISECONDS)
        httpClient = b.build()
    }


    /**
     * Описание get запроса
     */
    fun makeGetRequest(
        url: String
    ): String? {
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        val r = httpClient.newCall(request).execute()

        return if (r.isSuccessful) {
            val response = r.body()?.string()
            response
        } else {
            throw ConnectException("Ошибка запроса ${r.body()}")
        }
    }


    /**
     * Создание экземпляра класса статичной функцией
     */
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