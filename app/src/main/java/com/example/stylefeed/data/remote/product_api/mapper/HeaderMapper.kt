package com.example.stylefeed.data.remote.product_api.mapper

import com.example.stylefeed.data.remote.product_api.dto.HeaderDto
import com.example.stylefeed.domain.model.Header

fun HeaderDto.toDomain(): Header {
    return Header(
        title = title ?: "",
        iconUrl = iconURL,
        linkUrl = linkURL
    )
}
