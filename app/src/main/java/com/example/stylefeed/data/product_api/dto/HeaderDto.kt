package com.example.stylefeed.data.product_api.dto

import kotlinx.serialization.Serializable

@Serializable
data class HeaderDto(
    val title: String? = null,
    val iconURL: String? = null,
    val linkURL: String? = null
)