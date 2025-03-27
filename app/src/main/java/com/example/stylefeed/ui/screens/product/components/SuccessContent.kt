package com.example.stylefeed.ui.screens.product.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.stylefeed.designsystem.components.AnimatedStickyHeader
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.ui.screens.product.components.sections.SectionsList
import com.example.stylefeed.ui.screens.product.interactions.rememberStickyHeaderState

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