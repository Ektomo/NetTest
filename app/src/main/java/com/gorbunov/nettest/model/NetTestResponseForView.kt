package com.gorbunov.nettest.model


import kotlinx.serialization.Serializable


@Serializable
data class NetTestItem(
    val category: String,
    val count: Int
)