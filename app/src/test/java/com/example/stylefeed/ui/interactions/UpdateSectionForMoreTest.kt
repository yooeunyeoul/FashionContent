package com.example.stylefeed.ui.screens.product.interactions

import com.example.stylefeed.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class UpdateSectionForMoreTest {

    @Test
    fun `updateSectionForMore 는 visibleItemCount를 증가시키고 recentlyAddedImageUrl을 계산한다`() {
        // Given
        val products = List(5) { index ->
            Product(
                linkUrl = "https://example.com/$index",
                thumbnailUrl = "https://example.com/image$index.jpg",
                brandName = "Brand $index",
                formattedPrice = "10000원",
                saleRate = 10,
                hasCoupon = false
            )
        }

        val initial = SectionState(
            section = Section(null, Content.GridContent(products), null),
            visibleItemCount = 1,
            totalItemCount = 5
        )

        // When
        val (updatedList, recentlyAdded) = updateSectionForMore(listOf(initial), 0)

        // Then
        val updated = updatedList[0]
        assertTrue(updated.visibleItemCount > initial.visibleItemCount)
        assertTrue(recentlyAdded.isNotEmpty())
        assertTrue(recentlyAdded.all { it.contains("example.com") })
    }
}