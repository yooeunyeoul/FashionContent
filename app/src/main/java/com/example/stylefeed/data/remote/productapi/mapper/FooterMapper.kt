package com.example.stylefeed.data.remote.productapi.mapper

import com.example.stylefeed.data.remote.productapi.dto.FooterDto
import com.example.stylefeed.domain.model.Footer
import com.example.stylefeed.domain.model.FooterType

fun FooterDto.toDomain(): Footer = Footer(
    type = when(type?.uppercase()) {
        "MORE" -> FooterType.MORE
        "REFRESH" -> FooterType.REFRESH
        else -> FooterType.UNKNOWN
    },
    title = title ?: "",
    iconUrl = iconURL
)