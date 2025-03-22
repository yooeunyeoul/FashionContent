
package com.example.stylefeed.data.product_api.dto

import kotlinx.serialization.Serializable

@Serializable
data class SectionDto(
    val header: HeaderDto? = null,
    val contents: ContentsDto? = null,
    val footer: FooterDto? = null
)