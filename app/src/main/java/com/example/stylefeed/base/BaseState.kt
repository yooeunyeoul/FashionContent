package com.example.stylefeed.base

import com.airbnb.mvrx.MavericksState
import com.example.stylefeed.domain.model.ApiError

interface BaseState : MavericksState {
    val apiError: ApiError?
}