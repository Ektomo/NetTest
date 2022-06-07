package com.gorbunov.nettest.model

/**
 * Класс содержит стейты для [com.gorbunov.nettest.viewmodels.NetTestViewModel]
 * в зависимости от состояния класса вызывается соотвествующая функция для
 *   отрисовки в [com.gorbunov.nettest.screens.NetTestScreen]
 */
sealed class NetTestViewState {
    object Loading : NetTestViewState()
    data class Error(val e: Exception) : NetTestViewState()
    data class NetTestOk(val data: List<NetTestItem>) : NetTestViewState()
}
