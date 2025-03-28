package com.example.stylefeed.helper

import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Product
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.usecase.GetSectionsUseCase
import com.example.stylefeed.ui.screens.product.viewmodel.ProductState
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf

fun createTestSectionState(
    visibleCount: Int = 1,
    totalCount: Int = 5,
    productCount: Int = 5
): SectionState {
    val products = List(productCount) { index ->
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
        section = Section(
            header = null,
            content = Content.GridContent(products),
            footer = null
        ),
        visibleItemCount = visibleCount,
        totalItemCount = totalCount
    )
}

fun mockSections(getSectionsUseCase: GetSectionsUseCase, vararg sections: SectionState) {
    coEvery { getSectionsUseCase() } returns flowOf(sections.toList())
}

fun printState(state: ProductState) {
    println("🧪 visibleItemCounts: ${state.sections()?.map { it.visibleItemCount }}")
    println("🧪 recentlyAdded: ${state.recentlyAddedImageUrl}")
    println("🧪 loadingMap: ${state.sectionUiLoadingMap}")
}
