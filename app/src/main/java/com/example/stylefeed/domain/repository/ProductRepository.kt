package com.example.stylefeed.domain.repository

import com.example.stylefeed.domain.model.Section
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getSections(): Flow<List<Section>>
}