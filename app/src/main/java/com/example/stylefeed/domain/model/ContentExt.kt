package com.example.stylefeed.domain.model

fun Content.shuffleContent(): Content = when (this) {
    is Content.BannerContent -> copy(banners = banners.shuffled())
    is Content.GridContent -> copy(products = products.shuffled())
    is Content.ScrollContent -> copy(products = products.shuffled())
    is Content.StyleContent -> copy(styles = styles.shuffled())
    else -> this
}


fun Content.getItemIds(): Set<String> = when (this) {
    is Content.BannerContent -> banners.map { it.linkUrl }.toSet()
    is Content.GridContent -> products.map { it.linkUrl }.toSet()
    is Content.ScrollContent -> products.map { it.linkUrl }.toSet()
    is Content.StyleContent -> styles.map { it.linkUrl }.toSet()
    else -> emptySet()
}

val Content.imageAspectRatio: Float
    get() = when (this) {
        is Content.BannerContent -> 1f             // 1024f / 1024f
        is Content.GridContent -> 1f               // 320f / 320f
        is Content.ScrollContent -> 320f / 427f    // 스크롤 형태
        is Content.StyleContent -> 500f / 700f     // 스타일 형태
        Content.UnknownContent -> 1f
    }