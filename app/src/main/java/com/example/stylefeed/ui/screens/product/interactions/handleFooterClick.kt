package com.example.stylefeed.ui.screens.product.interactions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import com.example.stylefeed.designsystem.theme.Dimensions.ListBottomPadding
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.ui.screens.product.viewmodel.ProductEvent
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
            snapshotFlow { sectionHeights[sectionIndex] }
                .mapNotNull { it }
                .filter { newHeight -> newHeight > previousHeight }
                .take(1)
                .collect { newHeight ->
                    val animatedHeight = Animatable(previousHeight)
                    animatedHeight.animateTo(
                        targetValue = newHeight + ListBottomPadding.value,
                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                    )

                    sectionHeights[sectionIndex] = animatedHeight.value

                    // 확장 후 현재 보이는 영역 확인 후 스크롤 수행
                    val viewportHeight = listState.layoutInfo.viewportEndOffset.toFloat()
                    val sectionItemInfo = listState.layoutInfo.visibleItemsInfo
                        .firstOrNull { it.index == sectionIndex }

                    if (sectionItemInfo != null) {
                        val sectionBottomOffset = sectionItemInfo.offset + animatedHeight.value
                        if (sectionBottomOffset > viewportHeight) {
                            val scrollByAmount = sectionBottomOffset - viewportHeight
                            listState.animateScrollBy(scrollByAmount + ListBottomPadding.value)
                        }
                    }
                }
        }
    }
}