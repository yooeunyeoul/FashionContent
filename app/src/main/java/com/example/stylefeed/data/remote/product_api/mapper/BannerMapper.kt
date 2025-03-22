package com.example.stylefeed.data.remote.product_api.mapper

import com.example.stylefeed.data.remote.product_api.dto.BannerDto
import com.example.stylefeed.domain.model.Banner

fun BannerDto.toDomain(): Banner = Banner(
    linkUrl = linkURL ?: "",
    thumbnailUrl = thumbnailURL ?: "",
    title = title ?: "",
    description = description ?: "",
    keyword = keyword ?: ""
)