import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.stylefeed.ui.screens.product.ProductScreenContent
import com.example.stylefeed.ui.screens.product.components.toast.RefreshNotificationBanner
import com.example.stylefeed.ui.screens.product.interactions.handleFooterClick
import com.example.stylefeed.ui.screens.product.viewmodel.ProductEffect
import com.example.stylefeed.ui.screens.product.viewmodel.ProductState
import com.example.stylefeed.ui.screens.product.viewmodel.ProductViewModel


@Composable
fun ProductScreen(viewModel: ProductViewModel = mavericksViewModel()) {
    val sectionsAsync by viewModel.collectAsState(ProductState::sections)
    val recentlyAddedIds by viewModel.collectAsState(ProductState::recentlyAddedImageUrl)
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val apiError by viewModel.collectAsState(ProductState::apiError)
    val sectionHeights = remember { mutableStateMapOf<Int, Float>() }

    val sectionLoadingMap by viewModel.collectAsState(ProductState::sectionUiLoadingMap)
    var refreshBannerTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProductEffect.ShowRefreshSnackBar -> {
                    refreshBannerTrigger = true
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        ProductScreenContent(
            sectionsAsync = sectionsAsync,
            recentlyAddedIds = recentlyAddedIds,
            listState = listState,
            sectionHeights = sectionHeights,
            apiError = apiError,
            onFooterClick = { _, footerType, sectionIndex ->
                handleFooterClick(
                    onFooterClicked = viewModel::onEvent,
                    footerType = footerType,
                    sectionIndex = sectionIndex,
                    listState = listState,
                    sectionHeights = sectionHeights,
                    scope = scope
                )
            }, onRetryClick = viewModel::onEvent,
            sectionLoadingMap = sectionLoadingMap
        )
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            RefreshNotificationBanner(
                trigger = refreshBannerTrigger,
                onDismiss = {
                    refreshBannerTrigger = false
                }
            )
        }
    }
}
