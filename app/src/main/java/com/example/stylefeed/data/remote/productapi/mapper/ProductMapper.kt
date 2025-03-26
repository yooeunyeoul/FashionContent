package com.example.stylefeed.data.remote.productapi.mapper

import com.example.stylefeed.data.remote.productapi.dto.ProductDto
import com.example.stylefeed.domain.model.Product

fun ProductDto.toDomain(): Product = Product(
    linkUrl = linkURL ?: "",
    thumbnailUrl = thumbnailURL ?: "",
    brandName = brandName ?: "",
    formattedPrice = (price ?: 0).formatWithComma(),
    saleRate = saleRate ?: 0,
    hasCoupon = hasCoupon ?: false
)

// 숫자를 콤마로 변환하는 확장함수
fun Int.formatWithComma(): String = "%,d".format(this)