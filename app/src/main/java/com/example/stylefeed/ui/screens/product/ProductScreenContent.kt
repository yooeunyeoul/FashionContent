package com.example.stylefeed.ui.screens.product

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.example.stylefeed.domain.model.ApiError
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.ui.screens.product.components.ErrorContent
import com.example.stylefeed.ui.screens.product.components.LoadingContent
import com.example.stylefeed.ui.screens.product.components.SuccessContent
import com.example.stylefeed.ui.viewmodel.ProductEvent

@Composable
fun ProductScreenContent(
    sectionsAsync: Async<List<SectionState>>,
    recentlyAddedIds: Set<String>,
    listState: LazyListState,
    sectionHeights: MutableMap<Int, Float>,
    apiError: ApiError?,
    onFooterClick: (SectionState, FooterType, Int) -> Unit,
    onRetryClick:(ProductEvent) -> Unit
) {
    when (sectionsAsync) {
        is Loading -> LoadingContent()
        is Success -> SuccessContent(
            sections = sectionsAsync(),
            recentlyAddedIds = recentlyAddedIds,
            listState = listState,
            sectionHeights = sectionHeights,
            onFooterClick = onFooterClick
        )
        is Fail -> ErrorContent(apiError){
            onRetryClick(ProductEvent.OnNetworkRetryClicked)
        }
        else -> Unit
    }
}