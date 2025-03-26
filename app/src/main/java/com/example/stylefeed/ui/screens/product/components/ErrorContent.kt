package com.example.stylefeed.ui.screens.product.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.ApiError

@Composable
fun ErrorContent(
    error: ApiError?,
    onRetry: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (error) {
                    is ApiError.NetworkError -> "네트워크 연결에 실패했습니다."
                    is ApiError.ServerError -> "서버에 문제가 발생했습니다."
                    is ApiError.UnknownError -> "알 수 없는 오류가 발생했습니다."
                    else -> "오류가 발생했습니다."
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRetry) {
                Text("재시도")
            }
        }
    }
}