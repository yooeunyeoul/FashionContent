package com.example.stylefeed.data.product_api.dto

import kotlinx.serialization.Serializable

@Serializable
data class StyleDto(
    val linkURL: String? = null,
    val thumbnailURL: String? = null
)