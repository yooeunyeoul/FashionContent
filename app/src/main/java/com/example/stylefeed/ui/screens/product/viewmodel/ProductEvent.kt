package com.example.stylefeed.ui.screens.product.viewmodel

import com.example.stylefeed.base.BaseUiEffect
import com.example.stylefeed.base.BaseUiEvent
import com.example.stylefeed.domain.model.FooterType

sealed class ProductEvent : BaseUiEvent {
    data class OnFooterClicked(val sectionIndex: Int, val footerType: FooterType) : ProductEvent()
    data object OnNetworkRetryClicked : ProductEvent()
}

sealed interface ProductEffect : BaseUiEffect