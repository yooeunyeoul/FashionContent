package com.example.stylefeed.ui.screens.product.interactions

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.ui.viewmodel.ProductEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

fun handleFooterClick(
    onFooterClicked: (ProductEvent) -> Unit, // 이벤트 전달
    footerType: FooterType,
    sectionIndex: Int,
    listState: LazyListState,
    sectionHeights: MutableMap<Int, Float>,
    scope: CoroutineScope
) {
    val previousHeight = sectionHeights[sectionIndex] ?: 0f

    onFooterClicked(ProductEvent.OnFooterClicked(sectionIndex, footerType))

    if (footerType == FooterType.MORE) {
        scope.launch {
            snapshotFlow { sectionHeights[sectionIndex] } // 🔥 UI 갱신 대기 명확히 처리
                .mapNotNull { it }
                .filter { newHeight -> newHeight > previousHeight } // 높이 증가 확인
                .take(1) // 최초 한번만
                .collect { newHeight ->
                    val increasedHeight = newHeight - previousHeight
                    listState.animateScrollBy(increasedHeight)
                }
        }
    }
}