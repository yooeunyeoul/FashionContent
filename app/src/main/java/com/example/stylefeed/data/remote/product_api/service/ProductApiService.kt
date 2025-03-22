package com.example.stylefeed.data.remote.product_api.service

import com.example.stylefeed.data.remote.product_api.dto.RootDto
import com.example.stylefeed.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ProductApiService {
    @GET("interview/list.json")
    fun fetchSections(): Flow<NetworkResult<RootDto>>
}