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

@Composable
fun NetTestScreen(viewModel: NetTestViewModel) {

    val curState = viewModel._curState.collectAsState()

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

    LaunchedEffect(key1 = Unit){
        viewModel.getData()
    }
}