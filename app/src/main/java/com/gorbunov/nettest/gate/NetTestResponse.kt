package com.gorbunov.nettest.gate

import kotlinx.serialization.Serializable


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