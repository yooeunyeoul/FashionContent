package com.example.stylefeed.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.ui.common.BannerSlider
import com.example.stylefeed.ui.common.Footer
import com.example.stylefeed.ui.common.Header
import com.example.stylefeed.ui.common.ProductGrid
import com.example.stylefeed.ui.common.ProductHorizontalList
import com.example.stylefeed.ui.common.StyleGrid

@Composable
fun SectionView(section: Section, isSectionVisible: Boolean) {
    Column {
        section.header?.let { Header(header = it) }
        when (val content = section.content) {
            is Content.BannerContent -> BannerSlider(content.banners, isVisible = isSectionVisible)
            is Content.GridContent -> ProductGrid(content.products)
            is Content.ScrollContent -> ProductHorizontalList(content.products)
            is Content.StyleContent -> StyleGrid(content.styles)
            is Content.UnknownContent -> Text("지원하지 않는 컨텐츠입니다.")
        }
        section.footer?.let { Footer(it) }
    }
}