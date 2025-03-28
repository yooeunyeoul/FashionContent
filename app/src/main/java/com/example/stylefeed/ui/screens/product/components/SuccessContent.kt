package com.example.stylefeed.ui.screens.product.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stylefeed.designsystem.components.AnimatedStickyHeader
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.ui.screens.product.components.sections.SectionsList
import com.example.stylefeed.ui.screens.product.interactions.rememberStickyHeaderState
import com.example.stylefeed.ui.screens.product.previewdata.PreviewData

@Composable
fun SuccessContent(
    sections: List<SectionState>,
    recentlyAddedIds: Set<String>,
    listState: LazyListState,
    sectionHeights: MutableMap<Int, Float>,
    onFooterClick: (SectionState, FooterType, Int) -> Unit,
    sectionLoadingMap: Map<Int, Boolean>,
) {
    val isVisible = remember { mutableStateOf(true) }
    val currentStickyHeader = rememberStickyHeaderState(sections, listState, sectionHeights)

    Box(modifier = Modifier.fillMaxSize()) {
        SectionsList(
            sectionStates = sections,
            isVisible = isVisible.value,
            listState = listState,
            sectionHeights = sectionHeights,
            recentlyAddedIds = recentlyAddedIds,
            onFooterClick = onFooterClick,
            sectionLoadingMap = sectionLoadingMap
        )
        AnimatedStickyHeader(headerText = currentStickyHeader.value)
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessContentPreview() {
    val listState = rememberLazyListState()
    val sectionHeights = remember { mutableStateMapOf<Int, Float>() }

    SuccessContent(
        sections = PreviewData.allSections,
        recentlyAddedIds = emptySet(),
        listState = listState,
        sectionHeights = sectionHeights,
        onFooterClick = { _, _, _ -> },
        sectionLoadingMap = mapOf(0 to false, 1 to true) // 로딩 상태 테스트 가능
    )
}
