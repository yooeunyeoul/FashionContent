package com.example.stylefeed.designsystem.components.containers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylefeed.ui.theme.FashionContentTheme

@Composable
fun SectionContainer(
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column {
        header?.invoke()
        content()
        footer?.invoke()
    }
}

@Preview(showBackground = true, name = "SectionContainer - Header, Content, Footer")
@Composable
fun SectionContainerPreview_All() {
    FashionContentTheme {
        SectionContainer(
            header = { Text("헤더", modifier = Modifier.padding(8.dp)) },
            footer = { Text("푸터", modifier = Modifier.padding(8.dp)) },
            content = { Text("메인 컨텐츠", modifier = Modifier.padding(8.dp)) }
        )
    }
}

@Preview(showBackground = true, name = "SectionContainer - Only Content")
@Composable
fun SectionContainerPreview_ContentOnly() {
    FashionContentTheme {
        SectionContainer(
            content = { Text("메인 컨텐츠만", modifier = Modifier.padding(8.dp)) }
        )
    }
}

@Preview(showBackground = true, name = "SectionContainer - Header and Content")
@Composable
fun SectionContainerPreview_HeaderContent() {
    FashionContentTheme {
        SectionContainer(
            header = { Text("헤더", modifier = Modifier.padding(8.dp)) },
            content = { Text("메인 컨텐츠", modifier = Modifier.padding(8.dp)) }
        )
    }
}

@Preview(showBackground = true, name = "SectionContainer - Content and Footer")
@Composable
fun SectionContainerPreview_ContentFooter() {
    FashionContentTheme {
        SectionContainer(
            content = { Text("메인 컨텐츠", modifier = Modifier.padding(8.dp)) },
            footer = { Text("푸터", modifier = Modifier.padding(8.dp)) }
        )
    }
}