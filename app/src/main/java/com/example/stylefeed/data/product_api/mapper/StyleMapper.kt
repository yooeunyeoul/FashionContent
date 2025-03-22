package com.example.stylefeed.data.product_api.mapper

import com.example.stylefeed.domain.Style
import com.example.stylefeed.data.product_api.dto.StyleDto

fun StyleDto.toDomain(): Style = Style(
    linkUrl = linkURL ?: "",
    thumbnailUrl = thumbnailURL ?: ""
)