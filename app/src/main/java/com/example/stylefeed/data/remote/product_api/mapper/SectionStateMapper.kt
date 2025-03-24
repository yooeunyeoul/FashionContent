package com.example.stylefeed.data.remote.product_api.mapper

import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.model.SectionState

fun List<Section>.toSectionStates(): List<SectionState> {
    return map { section ->
        val totalCount = when (section.content) {
            is Content.GridContent -> section.content.products.size
            is Content.ScrollContent -> section.content.products.size
            is Content.BannerContent -> section.content.banners.size
            is Content.StyleContent -> section.content.styles.size
            else -> 0
        }

        val visibleCount = when (section.content) {
            is Content.GridContent -> minOf(6, totalCount)
            else -> totalCount // Grid가 아닌 경우엔 모두 보여줌
        }

        SectionState(
            section = section,
            visibleItemCount = visibleCount,
            totalItemCount = totalCount
        )
    }
}