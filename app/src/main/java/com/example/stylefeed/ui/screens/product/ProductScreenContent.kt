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
import com.example.stylefeed.designsystem.components.ErrorStateView
import com.example.stylefeed.designsystem.components.LoadingIndicator
import com.example.stylefeed.ui.screens.product.components.SuccessContent
import com.example.stylefeed.ui.screens.product.viewmodel.ProductEvent

@Composable
fun ProductScreenContent(
    sectionsAsync: Async<List<SectionState>>,
    recentlyAddedIds: Set<String>,
    listState: LazyListState,
    sectionHeights: MutableMap<Int, Float>,
    apiError: ApiError?,
    onFooterClick: (SectionState, FooterType, Int) -> Unit,
    onRetryClick: (ProductEvent) -> Unit,
    sectionLoadingMap: Map<Int, Boolean>
) {
    when (sectionsAsync) {
        is Loading -> LoadingIndicator()
        is Success -> SuccessContent(
            sections = sectionsAsync(),
            recentlyAddedIds = recentlyAddedIds,
            listState = listState,
            sectionHeights = sectionHeights,
            onFooterClick = onFooterClick,
            sectionLoadingMap = sectionLoadingMap
        )

        is Fail ->
            ErrorStateView(
                message = when (apiError) {
                    is ApiError.NetworkError -> "네트워크 연결에 실패했습니다."
                    is ApiError.ServerError -> "서버에 문제가 발생했습니다."
                    is ApiError.UnknownError -> "알 수 없는 오류가 발생했습니다."
                    else -> "오류가 발생했습니다."
                },
                onRetry = { onRetryClick(ProductEvent.OnNetworkRetryClicked) }
            )

        else -> Unit
    }

}
