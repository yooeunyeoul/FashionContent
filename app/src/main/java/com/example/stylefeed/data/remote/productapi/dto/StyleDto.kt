package com.example.stylefeed.data.remote.productapi.dto

import kotlinx.serialization.Serializable

@Serializable
data class StyleDto(
    val linkURL: String? = null,
    val thumbnailURL: String? = null
)