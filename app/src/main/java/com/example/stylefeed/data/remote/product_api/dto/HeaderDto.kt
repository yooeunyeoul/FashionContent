package com.example.stylefeed.data.remote.product_api.dto

import kotlinx.serialization.Serializable

@Serializable
data class HeaderDto(
    val title: String? = null,
    val iconURL: String? = null,
    val linkURL: String? = null
)