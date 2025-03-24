package com.example.stylefeed.ui.common
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Footer
import com.example.stylefeed.domain.model.FooterType

@Composable
fun Footer(
    footer: Footer,
    onFooterClick: (FooterType) -> Unit
) {
    Button(
        onClick = { onFooterClick(footer.type) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(footer.title)
    }
}