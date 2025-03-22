package com.example.stylefeed.domain

data class Footer(
    val type: FooterType,
    val title: String,
    val iconUrl: String?
)

enum class FooterType {
    MORE, REFRESH, UNKNOWN
}