
package com.example.stylefeed.data.remote.product_api.dto

import kotlinx.serialization.Serializable

@Serializable
data class SectionDto(
    val header: HeaderDto? = null,
    val contents: ContentsDto? = null,
    val footer: FooterDto? = null
)