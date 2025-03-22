package com.example.stylefeed.data.product_api.mapper

import com.example.stylefeed.domain.Header
import com.example.stylefeed.data.product_api.dto.HeaderDto

fun HeaderDto.toDomain(): Header {
    return Header(
        title = title ?: "",
        iconUrl = iconURL,
        linkUrl = linkURL
    )
}
