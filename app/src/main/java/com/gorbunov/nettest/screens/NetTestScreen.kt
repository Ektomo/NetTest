package com.gorbunov.nettest.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gorbunov.nettest.model.NetTestViewState
import com.gorbunov.nettest.viewmodels.NetTestViewModel
import com.gorbunov.nettest.views.CourseListView
import com.gorbunov.nettest.views.LoadingView


/**
 * Отрисовка основного экрана
 *
 * @param ViewModel
 * @see com.gorbunov.nettest.viewmodels.NetTestViewModel
 */
@Composable
fun NetTestScreen(viewModel: NetTestViewModel) {

    /**
     * Переенная содержит текущее состояние, берет из StateFlow вью модели
     *  состояние не мутабельное, его не возможно изменить из вью
     */
    val curState = viewModel.curState.collectAsState()

    /**
     * Собираем возможные экраны и отображаем в зависимости от текущего состояния
     */
    when(val state = curState.value){
        is NetTestViewState.Error ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.e.message ?: ""
                )
        }
        NetTestViewState.Loading -> LoadingView()
        is NetTestViewState.NetTestOk -> {
            CourseListView(data = state.data)
        }
    }

    /**
     * Функция запуска загрузки списка, благодаря использованию константы в параметре key1 гарантируется загрузка только на входе в экран
     */
    LaunchedEffect(key1 = Unit){
        viewModel.getData()
    }
}