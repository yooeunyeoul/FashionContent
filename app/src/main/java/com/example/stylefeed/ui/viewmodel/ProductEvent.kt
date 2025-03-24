package com.example.stylefeed.ui.viewmodel

import com.example.stylefeed.base.UiEffect
import com.example.stylefeed.base.UiEvent
import com.example.stylefeed.domain.model.FooterType

sealed class ProductEvent : UiEvent {
    data class OnFooterClicked(val sectionIndex: Int, val footerType: FooterType) : ProductEvent()
}

sealed class ProductEffect : UiEffect {
    data object ShowContentRefreshedToast : ProductEffect()
    data object ShowNoMoreContentToast : ProductEffect()
}