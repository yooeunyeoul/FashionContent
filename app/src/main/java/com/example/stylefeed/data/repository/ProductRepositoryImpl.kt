package com.example.stylefeed.data.repository

import com.example.stylefeed.data.remote.product_api.mapper.toDomain
import com.example.stylefeed.data.remote.product_api.service.ProductApiService
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.repository.ProductRepository
import com.example.stylefeed.utils.mapResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ProductApiService
) : ProductRepository {

    override fun getSections(): Flow<List<Section>> =
        apiService.fetchSections().mapResult { rootDto ->
            rootDto.data.map { it.toDomain() }
        }
}