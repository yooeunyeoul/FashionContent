package com.example.stylefeed.data.remote.product_api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProductDto(
    val linkURL: String? = null,
    val thumbnailURL: String? = null,
    val brandName: String? = null,
    val price: Int? = 0,
    val saleRate: Int? = 0,
    val hasCoupon: Boolean? = false
)