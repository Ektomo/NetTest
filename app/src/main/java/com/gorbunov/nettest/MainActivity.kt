package com.gorbunov.nettest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gorbunov.nettest.gate.Gate
import com.gorbunov.nettest.ui.theme.NetTestTheme
import com.gorbunov.nettest.views.LoadingView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val a = rememberCoroutineScope()
                    LoadingView()
                    LaunchedEffect(key1 = Unit){
                        a.launch(context = Dispatchers.IO){
                            val gate = Gate.getInstance()
                            gate.makeRequest("https://raw.githubusercontent.com/netology-code/rn-task/master/netology.json")
                        }
                    }
                }
            }
        }
    }
}

