import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.ui.screens.SectionsList
import com.example.stylefeed.ui.viewmodel.ProductEvent
import com.example.stylefeed.ui.viewmodel.ProductState
import com.example.stylefeed.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProductScreen(viewModel: ProductViewModel = mavericksViewModel()) {
    val sectionsAsync by viewModel.collectAsState(ProductState::sections)
    val recentlyAddedIds by viewModel.collectAsState(ProductState::recentlyAddedImageUrl)
    val listState = rememberLazyListState()

    val isVisible = remember { mutableStateOf(false) }
    val currentStickyHeader = remember { mutableStateOf<String?>(null) }

    val deviceHeightPx = LocalConfiguration.current.screenHeightDp.dp.value
    val sectionHeights = remember { mutableStateMapOf<Int, Float>() }
    val firstIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val offset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val scope = rememberCoroutineScope()


    LaunchedEffect(sectionsAsync) {
        if (sectionsAsync is Success) {
            isVisible.value = true
        } else {
            isVisible.value = false
        }
    }

    // Sticky 헤더 업데이트
    LaunchedEffect(firstIndex, offset) {
        if (sectionsAsync is Success) {
            val sections = sectionsAsync.invoke()
            val sectionHeight = sectionHeights[firstIndex] ?: 0f
            currentStickyHeader.value = if (sectionHeight > deviceHeightPx && offset > 0) {
                sections?.getOrNull(firstIndex)?.section?.header?.title
            } else null
        }
    }

    Box(Modifier.fillMaxSize()) {
        when (val sections = sectionsAsync) {
            is Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is Success -> {
                SectionsList(
                    sectionStates = sections(),
                    isVisible = isVisible.value,
                    listState = listState,
                    sectionHeights = sectionHeights,
                    recentlyAddedIds = recentlyAddedIds,
                    { sectionState, footerType, sectionIndex ->
                        val previousHeight = sectionHeights[sectionIndex] ?: 0f

                        viewModel.onEvent(ProductEvent.OnFooterClicked(sectionIndex, footerType))

                        if (footerType == FooterType.MORE) {
                            scope.launch {
                                delay(200) // ✅ UI 갱신 대기

                                val newHeight = sectionHeights[sectionIndex] ?: 0f
                                val increasedHeight = newHeight - previousHeight

                                // 증가된 높이만큼만 이동
                                if (increasedHeight > 0f) {
                                    listState.animateScrollBy(increasedHeight)
                                }
                            }
                        }
                    }                )
                StickyHeader(headerText = currentStickyHeader.value)
            }

            is Fail -> Text("로딩 실패: ${sections.error.message}", Modifier.align(Alignment.Center))
            else -> Unit
        }
    }
}