package com.gorbunov.nettest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gorbunov.nettest.gate.Gate
import com.gorbunov.nettest.screens.NetTestScreen
import com.gorbunov.nettest.ui.theme.NetTestTheme
import com.gorbunov.nettest.viewmodels.NetTestViewModel
import com.gorbunov.nettest.views.LoadingView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    /**
     * Объявление вью модели для передачи в параметры. Так проще использовать если нужна будет навигация для Compose
     *  в данном примере навигация не нужна
     */
    private val viewModel: NetTestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetTestTheme {
                /**
                 * Подготовленная тема, можно настраивать плавающую кнопку, верхний тулбар, нижний тулбар и т.д.
                 */
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    /**
                     * Описание действия, внешнего вида и положения плавающей кнопки
                     */
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}, backgroundColor = Color.Cyan) {
                            Icon(Icons.Filled.Email, contentDescription = "Тест кнопка")
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) {

                    /**
                     * Вспомогательные поля из библиотеки accompanist для работы с визуадьными элементами
                     */
                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = MaterialTheme.colors.isLight

                    /**
                     * Вызов основного экрана
                     */
                    NetTestScreen(viewModel = viewModel)

                    /**
                     * Сравнять фон цветов сверху и перекрасить иконки
                     * SideEffect вызывается каждую перерисовку, тяжелые задачи сюда складывать нельзя
                     */
                    SideEffect {
                        systemUiController.setSystemBarsColor(
                            color = Color.Transparent,
                            darkIcons = useDarkIcons
                        )
                    }
                }
            }
        }
    }
}

