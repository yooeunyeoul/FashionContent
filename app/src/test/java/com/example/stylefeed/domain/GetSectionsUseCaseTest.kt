package com.example.stylefeed.domain

import com.example.stylefeed.data.remote.productapi.mapper.toSectionStates
import com.example.stylefeed.domain.model.Banner
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Product
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.model.Style
import com.example.stylefeed.domain.repository.ProductRepository
import com.example.stylefeed.domain.usecase.GetSectionsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSectionsUseCaseTest {

    private lateinit var repository: ProductRepository
    private lateinit var useCase: GetSectionsUseCase


    @Before
    fun setup() {
        repository = mockk()
        useCase = GetSectionsUseCase(repository)
    }

    @Test
    fun `repository 로부터 받은 섹션을 toSectionStates 로 변환한다`() = runTest {
        // Given
        val sections = listOf(Section(header = null,
            content = Content.GridContent(products = List(5) { index ->
                com.example.stylefeed.domain.model.Product(
                    linkUrl = "https://example.com/$index",
                    thumbnailUrl = "https://example.com/image$index.jpg",
                    brandName = "Brand $index",
                    formattedPrice = "10000원",
                    saleRate = 10,
                    hasCoupon = false
                )
            }),
            footer = null))

        coEvery { repository.getSections() } returns flowOf(sections)

        // When
        val result = useCase().first()

        // Then
        assertEquals(1, result.size)
        assertEquals(5, result[0].totalItemCount)
        assertEquals(
            5.coerceAtMost(6), result[0].visibleItemCount
        ) // visibleItemCount = min(6, total)
    }

    @Test
    fun `GridContent 는 최대 6개만 visible`() {
        val section = Section(header = null,
            content = Content.GridContent(products = List(10) {
                Product(
                    "url$it",
                    "thumb$it",
                    "brand",
                    "1000원",
                    10,
                    false
                )
            }),
            footer = null)

        val result = listOf(section).toSectionStates()[0]

        assertEquals(10, result.totalItemCount)
        assertEquals(6, result.visibleItemCount)
    }

    @Test
    fun `StyleContent 도 최대 6개만 visible`() {
        val section = Section(header = null,
            content = Content.StyleContent(styles = List(8) { Style("url$it", "thumb$it") }),
            footer = null)

        val result = listOf(section).toSectionStates()[0]

        assertEquals(8, result.totalItemCount)
        assertEquals(6, result.visibleItemCount)
    }

    @Test
    fun `BannerContent 는 전체 보여줌`() {
        val section = Section(header = null,
            content = Content.BannerContent(banners = List(4) {
                Banner(
                    "url",
                    "thumb",
                    "",
                    "",
                    ""
                )
            }),
            footer = null)

        val result = listOf(section).toSectionStates()[0]

        assertEquals(4, result.totalItemCount)
        assertEquals(4, result.visibleItemCount)
    }

    @Test
    fun `UnknownContent 는 0으로 처리`() {
        val section = Section(
            header = null, content = Content.UnknownContent, footer = null
        )

        val result = listOf(section).toSectionStates()[0]

        assertEquals(0, result.totalItemCount)
        assertEquals(0, result.visibleItemCount)
    }

    @Test
    fun `GridContent 가 6개 미만일 경우 visibleItemCount 는 totalItemCount 와 같다`() {
        val products = List(4) { index ->
            Product(
                linkUrl = "https://example.com/$index",
                thumbnailUrl = "https://example.com/image$index.jpg",
                brandName = "Brand $index",
                formattedPrice = "10000원",
                saleRate = 10,
                hasCoupon = false
            )
        }
        val section = Section(null, Content.GridContent(products), null)

        val state = listOf(section).toSectionStates()[0]

        assertEquals(products.size, state.totalItemCount)
        assertEquals(products.size, state.visibleItemCount)
    }
}