package com.example.stylefeed.ui.common
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Footer

@Composable
fun Footer(footer: Footer) {
    Button(
        onClick = { /* REFRESH 또는 MORE 처리 로직 */ },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(footer.title)
    }
}