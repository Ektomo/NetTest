package com.gorbunov.nettest

import com.gorbunov.nettest.gate.Gate
import com.gorbunov.nettest.model.NetTestItem
import com.gorbunov.nettest.model.NetTestResponse
import kotlinx.serialization.decodeFromString
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testGate(){
        val gate = Gate.getInstance()
        val response =
            gate.makeGetRequest("https://raw.githubusercontent.com/netology-code/rn-task/master/netology.json")
        val data = if (response != null) {
            val ntr = gate.format.decodeFromString<NetTestResponse>(response)
            ntr
        } else {
            null
        }
        if(data != null){
            val list = mutableListOf<NetTestItem>()
            data.data.forEach { item ->
                val category = item.direction.title
                val count = item.groups.sumOf { it.items.size }
                list.add(NetTestItem(category, count))
            }
            println(list)
        }

    }
}