package com.example.stylefeed.data.remote.productapi.service

import com.example.stylefeed.data.remote.productapi.dto.RootDto
import com.example.stylefeed.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ProductApiService {
    @GET("interview/list.json")
    fun fetchSections(): Flow<NetworkResult<RootDto>>
}