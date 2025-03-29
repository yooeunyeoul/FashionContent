package com.example.stylefeed.ui.screens.product.components.sections

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import com.example.stylefeed.designsystem.theme.Dimensions.ListBottomPadding
import com.example.stylefeed.designsystem.theme.FashionContentTheme
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.ui.screens.product.previewdata.PreviewData
import kotlinx.coroutines.flow.distinctUntilChanged

private const val FadeAnimationDurationMillis = 600

@Composable
fun SectionsList(
    sectionStates: List<SectionState>,
    isVisible: Boolean,
    listState: LazyListState,
    sectionHeights: MutableMap<Int, Float>,
    recentlyAddedIds: Set<String>,
    sectionLoadingMap: Map<Int, Boolean>,
    onFooterClick: (SectionState, FooterType, Int) -> Unit
) {
    val visibleBannerIndices = remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.map { it.index }.toSet()
        }.distinctUntilChanged()
            .collect {
                visibleBannerIndices.value = it
            }
    }

    Crossfade(
        targetState = isVisible,
        animationSpec = tween(FadeAnimationDurationMillis)
    ) { visible ->
        LazyColumn(
            modifier = if (visible) Modifier.fillMaxSize() else Modifier
                .fillMaxSize()
                .alpha(0f),
            state = listState,
            contentPadding = PaddingValues(bottom = ListBottomPadding)
        ) {
            sectionStates.forEachIndexed { index, sectionState ->
                val isLoading = sectionLoadingMap[index] == true
                item(key = sectionState.id) {
                    Box(
                        Modifier.onGloballyPositioned {
                            val height = it.size.height.toFloat()
                            if (sectionHeights[index] != height) {
                                sectionHeights[index] = height
                            }
                        }
                    ) {
                        val bannerVisible = visibleBannerIndices.value.contains(index)

                        SectionView(
                            sectionState,
                            bannerVisible,
                            recentlyAddedIds,
                            isLoading,
                            onFooterClick = { state, footerType ->
                                onFooterClick(state, footerType, index)
                            })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionsListPreview() {
    val listState = rememberLazyListState()
    val sectionHeights = remember { mutableStateMapOf<Int, Float>() }
    FashionContentTheme {
        SectionsList(
            sectionStates = PreviewData.allSections.shuffled(), // 🔁 순서 바꿔서 테스트
            isVisible = true,
            listState = listState,
            sectionHeights = sectionHeights,
            recentlyAddedIds = emptySet(),
            sectionLoadingMap = mapOf(0 to false, 1 to true), // 로딩 여부 섞어보기
            onFooterClick = { _, _, _ -> }
        )
    }
}
