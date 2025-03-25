package com.example.stylefeed.domain.model

fun Content.shuffleContent(): Content = when (this) {
    is Content.GridContent -> copy(products = products.shuffled())
    is Content.ScrollContent -> copy(products = products.shuffled())
    is Content.BannerContent -> copy(banners = banners.shuffled())
    is Content.StyleContent -> copy(styles = styles.shuffled())
    else -> this
}