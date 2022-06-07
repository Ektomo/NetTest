package com.gorbunov.nettest.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.gorbunov.nettest.R


/**
 * Функция отображения загрузки с помощью библиотеки Lottie
 */
@Composable
fun LoadingView() {
    /**
     * Поле для загрузки файла анимации. Анимацию бесплатно можно выбрать с сайта https://lottiefiles.com/featured
     */
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    /**
     * Поле для остановки анимации после перерисовки
     */
    var isPlaying by remember { mutableStateOf(false) }
    /**
     * Поле для управления отображением анимации
     */
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying
    )


    /**
     * Отрисовка окна загрузки с анимацией
     */
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition,
                progress,
            )
        }
        Text(text = "Загрузка...", Modifier.align(BiasAlignment(0f, 0.6f)))
    }


    /**
     * Запуск анимации при входе на экран
     * Остановка анимации после перерисовки на другой экран
     */
    DisposableEffect(key1 = Unit) {
        isPlaying = true
        onDispose { isPlaying = false }
    }
}