package com.example.stylefeed.domain.usecase

import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Section>> = repository.getSections()
}