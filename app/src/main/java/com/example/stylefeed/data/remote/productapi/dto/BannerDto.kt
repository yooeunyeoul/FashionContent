package com.example.stylefeed.data.remote.productapi.dto

import kotlinx.serialization.Serializable

@Serializable
data class BannerDto(
    val linkURL: String? = null,
    val thumbnailURL: String? = null,
    val title: String? = null,
    val description: String? = null,
    val keyword: String? = null
)