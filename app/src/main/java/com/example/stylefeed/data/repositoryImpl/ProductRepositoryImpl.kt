package com.example.stylefeed.data.repositoryImpl

import com.example.stylefeed.data.remote.product_api.mapper.toDomain
import com.example.stylefeed.data.remote.product_api.service.ProductApiService
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.repository.ProductRepository
import com.example.stylefeed.utils.ApiException
import com.example.stylefeed.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ProductApiService
) : ProductRepository {

    override fun getSections(): Flow<List<Section>> =
        apiService.fetchSections().map { result ->
            when (result) {
                is NetworkResult.Success -> result.data.data.map { it.toDomain() } ?: emptyList()
                is NetworkResult.Error -> throw ApiException(result.code, result.message)
                is NetworkResult.NetworkError -> throw ApiException(-1, "네트워크 연결 오류")
            }
        }
}