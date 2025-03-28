package com.example.stylefeed.ui.interactions

import com.example.stylefeed.ui.screens.product.interactions.updateSectionForRefresh

import com.example.stylefeed.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class UpdateSectionForRefreshTest {

    @Test
    fun `updateSectionForRefresh 는 콘텐츠 순서를 변경한다`() {
        // Given
        val products = List(10) { index ->
            Product(
                linkUrl = "https://example.com/$index",
                thumbnailUrl = "https://example.com/image$index.jpg",
                brandName = "Brand $index",
                formattedPrice = "10000원",
                saleRate = 10,
                hasCoupon = false
            )
        }

        val original = SectionState(
            section = Section(null, Content.GridContent(products), null),
            visibleItemCount = 10,
            totalItemCount = 10
        )

        val originalIds = products.map { it.linkUrl }

        // When
        val updatedList = updateSectionForRefresh(listOf(original), 0)
        val shuffled = (updatedList[0].section.content as Content.GridContent).products.map { it.linkUrl }

        // Then
        assertEquals(originalIds.size, shuffled.size)
        assertNotEquals(originalIds, shuffled) // 순서 변경 확인
    }
}