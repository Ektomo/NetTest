package com.gorbunov.nettest.model

import kotlinx.serialization.Serializable


/**
 * Класс модели для парсинга ответа с сервера,
 * некоторые поля не берутся для удобства
 */

@Serializable
data class NetTestResponse(
    val data: List<Data>
)


@Serializable
data class Data(
    val groups: List<Group>,
    val direction: Direction
)

@Serializable
data class Direction (
    var id: String,
    var link: String,
    var title: String
)

@Serializable
data class Group (
    var id: String,
    var link: String,
    var items: List<Item>,
    var title: String
)

@Serializable
data class Item (
    var id: String,
    var link: String,
    var title: String
)