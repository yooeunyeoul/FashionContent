package com.example.stylefeed.domain.model

data class Section(
    val header: Header?,
    val content: Content,
    val footer: Footer?
)