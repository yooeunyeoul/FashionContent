package com.example.stylefeed.data.product_api.mapper

import com.example.stylefeed.data.product_api.dto.SectionDto
import com.example.stylefeed.domain.Content
import com.example.stylefeed.domain.Section

fun SectionDto.toDomain(): Section = Section(
    header = header?.toDomain(),
    content = contents?.toDomain() ?: Content.UnknownContent,
    footer = footer?.toDomain()
)