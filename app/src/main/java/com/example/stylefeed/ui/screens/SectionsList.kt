package com.example.stylefeed.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import com.example.stylefeed.domain.model.Section

private const val FadeAnimationDurationMillis = 600

@Composable
fun SectionsList(
    sections: List<Section>,
    isVisible: Boolean,
    listState: LazyListState,
    sectionHeights: MutableMap<Int, Float>,
) {
    Crossfade(targetState = isVisible, animationSpec = tween(FadeAnimationDurationMillis)) { visible ->
        val modifier = if (visible) Modifier.fillMaxSize() else Modifier.fillMaxSize().alpha(0f)
        LazyColumn(modifier = modifier, state = listState) {
            sections.forEachIndexed { index, section ->
                item(key = section.hashCode()) {
                    Box(Modifier.onGloballyPositioned {
                        sectionHeights[index] = it.size.height.toFloat()
                    }) {
                        SectionView(section)
                    }
                }
            }
        }
    }
}