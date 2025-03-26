package com.example.stylefeed.data.remote.product_api.mapper

import com.example.stylefeed.data.remote.product_api.dto.ProductDto
import com.example.stylefeed.domain.model.Product

fun ProductDto.toDomain(): Product = Product(
    linkUrl = linkURL ?: "",
    thumbnailUrl = thumbnailURL ?: "",
    brandName = brandName ?: "",
    price = price ?: 0,
    saleRate = saleRate ?: 0,
    hasCoupon = hasCoupon ?: false
)