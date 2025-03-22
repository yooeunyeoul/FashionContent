package com.example.stylefeed.data.remote.product_api.dto

import kotlinx.serialization.Serializable

/**
 * 최상위 JSON 구조
 */
@Serializable
data class RootDto(
    val data: List<SectionDto> = emptyList()
)