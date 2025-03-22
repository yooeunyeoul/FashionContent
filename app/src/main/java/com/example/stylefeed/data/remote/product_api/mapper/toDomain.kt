package com.example.stylefeed.data.remote.product_api.mapper

import com.example.stylefeed.data.remote.product_api.dto.RootDto
import com.example.stylefeed.domain.model.Section

fun RootDto.toDomain(): List<Section> =
    data?.map { it.toDomain() } ?: emptyList()