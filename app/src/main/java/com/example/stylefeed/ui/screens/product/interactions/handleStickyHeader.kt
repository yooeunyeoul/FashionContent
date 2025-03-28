package com.example.stylefeed.ui.screens.product.interactions
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.SectionState

@Composable
fun rememberStickyHeaderState(
    sections: List<SectionState>,
    listState: LazyListState,
    sectionHeights: Map<Int, Float>
): State<String?> {
    val deviceHeightPx = LocalConfiguration.current.screenHeightDp.dp.value
    val currentStickyHeader = remember { mutableStateOf<String?>(null) }

    val firstIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val offset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    LaunchedEffect(firstIndex, offset) {
        val sectionHeight = sectionHeights[firstIndex] ?: 0f
        currentStickyHeader.value = if (sectionHeight > deviceHeightPx && offset > 0) {
            sections.getOrNull(firstIndex)?.section?.header?.title
        } else null
    }

    return currentStickyHeader
}