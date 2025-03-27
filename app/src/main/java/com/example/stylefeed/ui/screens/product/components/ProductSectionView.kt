package com.example.stylefeed.ui.screens.product.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.stylefeed.designsystem.components.containers.SectionContainer
import com.example.stylefeed.designsystem.components.header.Header
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.model.imageAspectRatio
import com.example.stylefeed.ui.common.BannerSlider
import com.example.stylefeed.ui.common.Footer
import com.example.stylefeed.ui.common.ProductHorizontalList
import com.example.stylefeed.ui.common.grid.ProductGrid
import com.example.stylefeed.ui.common.grid.StyleGrid

@Composable
fun SectionView(
    sectionState: SectionState,
    isSectionVisible: Boolean,
    recentlyAddedIds: Set<String>,
    onFooterClick: (SectionState, FooterType) -> Unit
) {

    SectionContainer(
        header = sectionState.section.header?.let {
            {
                Header(
                    title = it.title,
                    iconUrl = it.iconUrl,
                    linkButtonText = it.linkUrl
                )
            }
        },
        footer = {
            val footer = sectionState.section.footer
            val shouldShowFooter =
                footer != null && (footer.type != FooterType.MORE || sectionState.visibleItemCount < sectionState.totalItemCount)
            if (shouldShowFooter && footer != null) {
                Footer(footer) { footerType ->
                    onFooterClick(sectionState, footerType)
                }
            }
        }) {
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

    }

}