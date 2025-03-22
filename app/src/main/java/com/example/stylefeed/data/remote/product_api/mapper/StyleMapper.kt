package com.example.stylefeed.data.remote.product_api.mapper

import com.example.stylefeed.data.remote.product_api.dto.StyleDto
import com.example.stylefeed.domain.model.Style

fun StyleDto.toDomain(): Style = Style(
    linkUrl = linkURL ?: "",
    thumbnailUrl = thumbnailURL ?: ""
)