package com.example.stylefeed.data.remote.product_api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContentsDto(
    val type: String? = null,
    val banners: List<BannerDto> = emptyList(),
    val goods: List<ProductDto> = emptyList(),
    val styles: List<StyleDto> = emptyList()
)