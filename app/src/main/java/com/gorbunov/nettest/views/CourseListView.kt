package com.gorbunov.nettest.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorbunov.nettest.model.NetTestItem


/**
 * Функция для отрисовки списка с загруженными данными
 */
@Composable
fun CourseListView(data: List<NetTestItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
    ) {
        item {
            /**
             * Отрисовка заголовка
             */
            Row(modifier = Modifier.fillMaxWidth()) {
                //Создание строки с разными стилями
                val annotatedText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)
                    ) {
                        append("Изучайте ")
                    }
                    withStyle(
                        style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)
                    ) {
                        append("актуальные темы")
                    }
                }
                Text(
                    annotatedText,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(start = 12.dp, end = 16.dp)
                )
            }
        }
        data.forEach { listItem ->
            item {
                /**
                 * Отрисовка элементов списка
                 */
                Divider(modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Column(modifier = Modifier.wrapContentSize()) {
                        Text(
                            text = listItem.category,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "${listItem.count} курсов",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Canvas(modifier = Modifier.size(40.dp), onDraw = {
                        drawCircle(color = Color.Gray, alpha = 0.15f)
                    })
                }
            }

        }
    }
}

//Превью для тестовой отрисовки
@Preview
@Composable
fun ListViewPreview() {
    CourseListView(
        data = listOf(
            NetTestItem("Маркетинг", 25),
            NetTestItem("Маркетинг", 25)
        )
    )
}