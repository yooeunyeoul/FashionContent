package com.example.stylefeed.data.remote.productapi.mapper

import com.example.stylefeed.data.remote.productapi.dto.SectionDto
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Section

fun SectionDto.toDomain(): Section = Section(
    header = header?.toDomain(),
    content = contents?.toDomain() ?: Content.UnknownContent,
    footer = footer?.toDomain()
)