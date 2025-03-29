package com.example.stylefeed.helper

import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Product
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.usecase.GetSectionsUseCase
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf

fun createTestSectionState(
    id: String = "test-${System.currentTimeMillis()}",
    visibleCount: Int = 1,
    totalCount: Int = 5,
    products: List<Product>? = null,
    productCount: Int = totalCount
): SectionState {
    val finalProducts = products ?: List(productCount) { index ->
        Product(
            linkUrl = "https://example.com/$index",
            thumbnailUrl = "https://example.com/image$index.jpg",
            brandName = "Brand $index",
            formattedPrice = "10000원",
            saleRate = 10,
            hasCoupon = false
        )
    }

    return SectionState(
        id = id, section = Section(
            header = null, content = Content.GridContent(finalProducts), footer = null
        ), visibleItemCount = visibleCount, totalItemCount = totalCount
    )
}

fun mockSections(getSectionsUseCase: GetSectionsUseCase, vararg sections: SectionState) {
    coEvery { getSectionsUseCase() } returns flowOf(sections.toList())
}

