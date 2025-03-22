package com.example.stylefeed.data.product_api.mapper

import com.example.stylefeed.data.product_api.dto.ProductDto
import com.example.stylefeed.domain.Product

fun ProductDto.toDomain(): Product = Product(
    linkUrl = linkURL ?: "",
    thumbnailUrl = thumbnailURL ?: "",
    brandName = brandName ?: "",
    price = price ?: 0,
    saleRate = saleRate ?: 0,
    hasCoupon = hasCoupon ?: false
)