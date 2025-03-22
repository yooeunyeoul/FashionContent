package com.example.stylefeed.domain

sealed class Content {
    data class BannerContent(val banners: List<Banner>) : Content()
    data class GridContent(val products: List<Product>) : Content()
    data class ScrollContent(val products: List<Product>) : Content()
    data class StyleContent(val styles: List<Style>) : Content()
    object UnknownContent : Content()
}