package com.gorbunov.nettest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorbunov.nettest.gate.Gate
import com.gorbunov.nettest.model.NetTestItem
import com.gorbunov.nettest.model.NetTestResponse
import com.gorbunov.nettest.model.NetTestViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString

/**
 * Класс вью модели для того, чтобы сохранять данные между пересозданиями и перерисовками экрана,
 *  а также работы с сетью
 */
class NetTestViewModel : ViewModel() {
    private val curStatePr = MutableStateFlow<NetTestViewState>(NetTestViewState.Loading)
    val curState: StateFlow<NetTestViewState> = curStatePr
    private val gate = Gate.getInstance()


    //Запрос на получение данных
    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            //Смена состояния для отправки сигнала перерисовки экрана на загрузку
            curStatePr.value = NetTestViewState.Loading
            try {
                val response =
                    gate.makeGetRequest("https://raw.githubusercontent.com/netology-code/rn-task/master/netology.json")
                val data = if (response != null) {
                    val ntr = gate.format.decodeFromString<NetTestResponse>(response)
                    ntr
                } else {
                    null
                }
                if (data != null) {
                    //Парсинг данных, пришедших с интернета
                    val list = mutableListOf<NetTestItem>()
                    data.data.forEach { item ->
                        val category = item.direction.title
                        val count = item.groups.sumOf { it.items.size }
                        list.add(NetTestItem(category, count))
                    }
                    //Смена состояния для отправки сигнала перерисовки экрана на список
                    curStatePr.value = NetTestViewState.NetTestOk(list)
                }else{
                    throw IllegalStateException("Не удалось загрузить данные")
                }
            } catch (e: Exception) {
                //Смена состояния для отправки сигнала перерисовки экрана на ошибку
                curStatePr.value = NetTestViewState.Error(e)
            }
        }
    }

}