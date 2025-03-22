package com.example.stylefeed.data.product_api.mapper

import com.example.stylefeed.domain.Footer
import com.example.stylefeed.data.product_api.dto.FooterDto
import com.example.stylefeed.domain.FooterType

fun FooterDto.toDomain(): Footer = Footer(
    type = when(type?.uppercase()) {
        "MORE" -> FooterType.MORE
        "REFRESH" -> FooterType.REFRESH
        else -> FooterType.UNKNOWN
    },
    title = title ?: "",
    iconUrl = iconURL
)