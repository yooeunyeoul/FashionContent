import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.ui.common.BannerSlider
import com.example.stylefeed.ui.common.Footer
import com.example.stylefeed.ui.common.Header
import com.example.stylefeed.ui.common.ProductGrid
import com.example.stylefeed.ui.common.ProductHorizontalList
import com.example.stylefeed.ui.common.StyleGrid
import com.example.stylefeed.ui.viewmodel.ProductState
import com.example.stylefeed.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Composable
fun ProductScreen(viewModel: ProductViewModel = mavericksViewModel()) {
    val sectionsAsync by viewModel.collectAsState(ProductState::sections)
    val (isVisible, setIsVisible) = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(sectionsAsync) {
        if (sectionsAsync is Success) {
            snapshotFlow { listState.layoutInfo.totalItemsCount }
                .filter { it > 0 }
                .first()

            delay(100) // Compose가 높이 계산할 시간을 줌
            listState.scrollToItem(0) // 강제로 최상단 이동

            delay(50) // 스크롤 적용 시간을 주고
            setIsVisible(true) // 화면 표시 활성화
        } else {
            setIsVisible(false) // 로딩/실패 상태에서는 숨김
        }
    }

    Box(Modifier.fillMaxSize()) {
        when (val sections = sectionsAsync) {
            is Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is Success -> {
                Crossfade(
                    targetState = isVisible,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                ) { visible ->
                    if (visible) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = listState
                        ) {
                            sections().forEach { section ->
                                item(key = section.hashCode()) {
                                    SectionView(section)
                                }
                            }
                        }
                    } else {
                        // 높이 측정용 (보이지 않음)
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().alpha(0f),
                            state = listState
                        ) {
                            sections().forEach { section ->
                                item(key = section.hashCode()) {
                                    SectionView(section)
                                }
                            }
                        }
                    }
                }
            }

            is Fail -> {
                Text(
                    text = "로딩 실패: ${sections.error.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> Unit
        }
    }
}

@Composable
fun SectionView(section: Section) {
    Column {
        section.header?.let { Header(it) }

        when (val content = section.content) {
            is Content.BannerContent -> BannerSlider(content.banners)
            is Content.GridContent -> ProductGrid(content.products)
            is Content.ScrollContent -> ProductHorizontalList(content.products)
            is Content.StyleContent -> StyleGrid(content.styles)
            is Content.UnknownContent -> Text("지원하지 않는 컨텐츠입니다.")
        }

        section.footer?.let { Footer(it) }
    }
}