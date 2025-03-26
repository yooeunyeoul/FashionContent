package com.example.stylefeed.ui.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Uninitialized
import com.example.stylefeed.base.BaseState
import com.example.stylefeed.domain.model.ApiError
import com.example.stylefeed.domain.model.SectionState

data class ProductState(
    val sections: Async<List<SectionState>> = Uninitialized,
    val recentlyAddedImageUrl: Set<String> = emptySet(),// 최근 추가된 섹션 별 아이템 index
    override val apiError: ApiError? = null
) : BaseState
