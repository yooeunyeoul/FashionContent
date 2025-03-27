package com.example.stylefeed.designsystem.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylefeed.designsystem.theme.FashionContentTheme

@Composable
fun ErrorStateView(
    message: String,
    retryButtonText: String = "재시도",
    onRetry: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = message)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRetry) {
                Text(retryButtonText)
            }
        }
    }
}

@Preview(showBackground = true, name = "Server Error")
@Composable
fun ErrorStateViewPreview_ServerError() {
    FashionContentTheme {
        ErrorStateView(
            message = "서버에 문제가 발생했습니다.",
            retryButtonText = "다시 시도하기",
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Unknown Error")
@Composable
fun ErrorStateViewPreview_UnknownError() {
    FashionContentTheme {
        ErrorStateView(
            message = "알 수 없는 오류가 발생했습니다.",
            retryButtonText = "새로고침",
            onRetry = {}
        )
    }
}