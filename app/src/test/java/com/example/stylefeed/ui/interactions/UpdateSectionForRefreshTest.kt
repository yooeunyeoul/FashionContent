package com.example.stylefeed.ui.interactions

import com.example.stylefeed.ui.screens.product.interactions.updateSectionForRefresh

import com.example.stylefeed.domain.model.*
import com.example.stylefeed.helper.createTestSectionState
import org.junit.Assert.*
import org.junit.Test

class UpdateSectionForRefreshTest {

    @Test
    fun `updateSectionForRefresh 는 콘텐츠 순서를 변경한다`() {
        // Given
        // Given
        val original = createTestSectionState(
            id = "refresh-test-section",
            visibleCount = 10,
            totalCount = 10,
            productCount = 10
        )

        val originalProducts = (original.section.content as Content.GridContent).products
        val originalIds = originalProducts.map { it.linkUrl }

        // When
        val updatedList = updateSectionForRefresh(listOf(original), 0)
        val shuffled =
            (updatedList[0].section.content as Content.GridContent).products.map { it.linkUrl }

        // Then
        assertEquals(originalIds.size, shuffled.size)
        assertNotEquals(originalIds, shuffled) // 순서 변경 확인
    }
}