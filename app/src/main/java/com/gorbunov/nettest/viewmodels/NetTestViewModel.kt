package com.gorbunov.nettest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorbunov.nettest.gate.Gate
import com.gorbunov.nettest.model.NetTestItem
import com.gorbunov.nettest.model.NetTestViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NetTestViewModel : ViewModel() {
    private val curState = MutableStateFlow<NetTestViewState>(NetTestViewState.Loading)
    val _curState: StateFlow<NetTestViewState> = curState
    private val gate = Gate.getInstance()


    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            curState.value = NetTestViewState.Loading
            try {
                val data =
                    gate.makeGetRequest("https://raw.githubusercontent.com/netology-code/rn-task/master/netology.json")
                if(data != null){
                    val list = mutableListOf<NetTestItem>()
                    data.data.forEach { item ->
                        val category = item.direction.title
                        val count = item.groups.sumOf { it.items.size }
                        list.add(NetTestItem(category, count))
                    }
                    curState.value = NetTestViewState.NetTestOk(list)
                }
            } catch (e: Exception) {
                curState.value = NetTestViewState.Error(e)
            }
        }
    }

}