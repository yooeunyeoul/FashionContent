package com.example.stylefeed.data.remote.productapi

import com.example.stylefeed.data.remote.dto.MockResponseDto
import com.example.stylefeed.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface MockApi {
    @GET("mock")
    fun getMockData(): Flow<NetworkResult<MockResponseDto>>
}
