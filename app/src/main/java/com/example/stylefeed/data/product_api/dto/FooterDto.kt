package com.example.stylefeed.data.product_api.dto

import kotlinx.serialization.Serializable

@Serializable
data class FooterDto(
    val type: String? = null,
    val title: String? = null,
    val iconURL: String? = null
)