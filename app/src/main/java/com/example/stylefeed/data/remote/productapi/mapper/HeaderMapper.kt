package com.example.stylefeed.data.remote.productapi.mapper

import com.example.stylefeed.data.remote.productapi.dto.HeaderDto
import com.example.stylefeed.domain.model.Header

fun HeaderDto.toDomain(): Header {
    return Header(
        title = title ?: "",
        iconUrl = iconURL,
        linkUrl = linkURL
    )
}
