package com.example.stylefeed.ui.screens.product.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Uninitialized
import com.example.stylefeed.base.BaseState
import com.example.stylefeed.domain.model.ApiError
import com.example.stylefeed.domain.model.SectionState

data class ProductState(
    val sections: Async<List<SectionState>> = Uninitialized,
    val recentlyAddedImageUrl: Set<String> = emptySet(),
    override val apiError: ApiError? = null,
    val sectionUiLoadingMap: Map<Int, Boolean> = emptyMap()
) : BaseState
