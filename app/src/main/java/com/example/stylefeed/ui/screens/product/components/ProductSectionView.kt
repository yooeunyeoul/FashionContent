package com.example.stylefeed.ui.screens.product.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.model.imageAspectRatio
import com.example.stylefeed.ui.common.BannerSlider
import com.example.stylefeed.ui.common.Footer
import com.example.stylefeed.ui.common.Header
import com.example.stylefeed.ui.common.ProductGrid
import com.example.stylefeed.ui.common.ProductHorizontalList
import com.example.stylefeed.ui.common.StyleGrid

@Composable
fun SectionView(
    sectionState: SectionState,
    isSectionVisible: Boolean,
    recentlyAddedIds: Set<String>,
    onFooterClick: (SectionState, FooterType) -> Unit
) {
    Column {
        sectionState.section.header?.let { Header(header = it) }
        when (val content = sectionState.visibleContent) {
            is Content.BannerContent ->
                BannerSlider(
                    content.banners,
                    isVisible = isSectionVisible,
                    imageAspectRatio = content.imageAspectRatio
                )

            is Content.GridContent ->
                ProductGrid(
                    content.products,
                    recentlyAddedIds,
                    imageAspectRatio = content.imageAspectRatio
                )

            is Content.ScrollContent ->
                ProductHorizontalList(
                    content.products,
                    recentlyAddedIds,
                    imageAspectRatio = content.imageAspectRatio
                )

            is Content.StyleContent ->
                StyleGrid(
                    content.styles,
                    recentlyAddedIds = recentlyAddedIds,
                    imageAspectRatio = content.imageAspectRatio
                )

            Content.UnknownContent ->
                Text("지원하지 않는 컨텐츠입니다.")
        }

        sectionState.section.footer?.let { footer ->
            val shouldShowFooter = when (footer.type) {
                FooterType.MORE -> sectionState.visibleItemCount < sectionState.totalItemCount
                else -> true
            }

            if (shouldShowFooter) {
                Footer(footer) { footerType ->
                    onFooterClick(sectionState, footerType)
                }
            }
        }
    }
}