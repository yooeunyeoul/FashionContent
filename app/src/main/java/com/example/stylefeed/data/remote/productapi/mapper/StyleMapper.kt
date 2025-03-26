package com.example.stylefeed.data.remote.productapi.mapper

import com.example.stylefeed.data.remote.productapi.dto.StyleDto
import com.example.stylefeed.domain.model.Style

fun StyleDto.toDomain(): Style = Style(
    linkUrl = linkURL ?: "",
    thumbnailUrl = thumbnailURL ?: ""
)