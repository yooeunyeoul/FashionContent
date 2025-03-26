package com.example.stylefeed.domain.model

data class Product(
    val linkUrl: String,
    val thumbnailUrl: String,
    val brandName: String,
    val formattedPrice: String,
    val saleRate: Int,
    val hasCoupon: Boolean
)