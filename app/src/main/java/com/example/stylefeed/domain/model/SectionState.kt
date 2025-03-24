package com.example.stylefeed.domain.model

data class SectionState(
    val section: Section,
    val visibleItemCount: Int,  // 현재 보여지는 개수
    val totalItemCount: Int     // 전체 데이터 개수
) {
    val visibleContent: Content
        get() = when (val content = section.content) {
            is Content.GridContent -> content.copy(products = content.products.take(visibleItemCount))
            is Content.ScrollContent -> content.copy(products = content.products.take(visibleItemCount))
            is Content.StyleContent -> content.copy(styles = content.styles.take(visibleItemCount))
            else -> content
        }
}