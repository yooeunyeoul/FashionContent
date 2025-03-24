package com.example.stylefeed.domain.usecase

import com.example.stylefeed.data.remote.product_api.mapper.toSectionStates
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<SectionState>> {
        return repository.getSections().map { sections ->
            sections.toSectionStates()
        }
    }
}