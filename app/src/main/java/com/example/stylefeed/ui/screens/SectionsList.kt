package com.example.stylefeed.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState

private const val FadeAnimationDurationMillis = 600

@Composable
fun SectionsList(
    sectionStates: List<SectionState>,
    isVisible: Boolean,
    listState: LazyListState,
    sectionHeights: MutableMap<Int, Float>,
    onFooterClick: (SectionState, FooterType, Int) -> Unit
) {
    val visibleBannerIndices by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.map { it.index }.toSet()
        }
    }

    Crossfade(targetState = isVisible, animationSpec = tween(FadeAnimationDurationMillis)) { visible ->
        LazyColumn(
            modifier = if (visible) Modifier.fillMaxSize() else Modifier.fillMaxSize().alpha(0f),
            state = listState
        ) {
            sectionStates.forEachIndexed { index, sectionState ->
                item(key = sectionState.hashCode()) {
                    Box(
                        Modifier.onGloballyPositioned {
                            sectionHeights[index] = it.size.height.toFloat()
                        }
                    ) {
                        val bannerVisible = visibleBannerIndices.contains(index)

                        SectionView(sectionState, bannerVisible, onFooterClick = { state, footerType ->
                            onFooterClick(state, footerType, index)
                        })
                    }
                }
            }
        }
    }
}