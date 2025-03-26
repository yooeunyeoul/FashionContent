package com.example.stylefeed.domain.model

fun Content.shuffleContent(): Content = when (this) {
    is Content.GridContent -> copy(products = products.shuffled())
    is Content.ScrollContent -> copy(products = products.shuffled())
    is Content.BannerContent -> copy(banners = banners.shuffled())
    is Content.StyleContent -> copy(styles = styles.shuffled())
    else -> this
}


fun Content.getItemIds(): Set<String> = when (this) {
    is Content.GridContent -> products.map { it.linkUrl }.toSet()
    is Content.ScrollContent -> products.map { it.linkUrl }.toSet()
    is Content.StyleContent -> styles.map { it.linkUrl }.toSet()
    is Content.BannerContent -> banners.map { it.linkUrl }.toSet()
    else -> emptySet()
}