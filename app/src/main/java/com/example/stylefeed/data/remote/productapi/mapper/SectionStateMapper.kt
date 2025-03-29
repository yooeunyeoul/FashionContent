package com.example.stylefeed.data.remote.productapi.mapper

import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.model.SectionState
import java.util.UUID

fun List<Section>.toSectionStates(): List<SectionState> {
    return mapIndexed { index, section ->
        val totalCount = when (section.content) {
            is Content.GridContent -> section.content.products.size
            is Content.ScrollContent -> section.content.products.size
            is Content.BannerContent -> section.content.banners.size
            is Content.StyleContent -> section.content.styles.size
            else -> 0
        }

        val visibleCount = when (section.content) {
            is Content.GridContent -> minOf(6, totalCount)
            is Content.StyleContent -> minOf(6, totalCount)
            else -> totalCount
        }

        val id = when (val content = section.content) {
            is Content.StyleContent -> "style-${content.styles.firstOrNull()?.linkUrl ?: UUID.randomUUID()}-$index"
            is Content.GridContent -> "grid-${content.products.firstOrNull()?.linkUrl ?: UUID.randomUUID()}-$index"
            is Content.ScrollContent -> "scroll-${content.products.firstOrNull()?.linkUrl ?: UUID.randomUUID()}-$index"
            is Content.BannerContent -> "banner-${content.banners.firstOrNull()?.linkUrl ?: UUID.randomUUID()}-$index"
            else -> "unknown-${UUID.randomUUID()}-$index"
        }

        SectionState(
            id = id,
            section = section,
            visibleItemCount = visibleCount,
            totalItemCount = totalCount
        )
    }
}