package com.example.stylefeed.ui

import app.cash.turbine.test
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.test.MavericksTestRule
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.Product
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.usecase.GetSectionsUseCase
import com.example.stylefeed.helper.createTestSectionState
import com.example.stylefeed.helper.mockSections
import com.example.stylefeed.ui.screens.product.viewmodel.ProductEffect
import com.example.stylefeed.ui.screens.product.viewmodel.ProductEvent
import com.example.stylefeed.ui.screens.product.viewmodel.ProductState
import com.example.stylefeed.ui.screens.product.viewmodel.ProductViewModel
import com.example.stylefeed.utils.ApiException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private val getSectionsUseCase: GetSectionsUseCase = mockk(relaxed = true)

    @get:Rule
    val mavericksTestRule = MavericksTestRule()

    @Test
    fun `초기 상태에서 fetchSections 성공`() = runTest {
        val testSection = createTestSectionState(visibleCount = 0, totalCount = 0)
        mockSections(getSectionsUseCase, testSection)

        val viewModel = ProductViewModel(ProductState(), getSectionsUseCase)

        viewModel.stateFlow.test {
            val initialState = awaitItem()
            assertTrue(initialState.sections is Success)
            assertEquals(listOf(testSection), initialState.sections.invoke())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `초기 상태에서 fetchSections 실패`() = runTest {
        coEvery { getSectionsUseCase() } returns flow {
            throw ApiException(500, "서버 에러")
        }

        val viewModel = ProductViewModel(ProductState(), getSectionsUseCase)

        viewModel.stateFlow.test {
            val state = awaitItem()
            assertTrue(state.sections is Fail)
            val error = (state.sections as Fail).error
            assertTrue(error is ApiException)
            assertEquals("서버 에러", error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `FooterType MORE 클릭 시 섹션이 확장되고 recentlyAddedImageUrl이 반영된다`() = runTest {
        val testSection = createTestSectionState(visibleCount = 1, totalCount = 5)
        mockSections(getSectionsUseCase, testSection)

        val viewModel = ProductViewModel(ProductState(), getSectionsUseCase)

        viewModel.onEvent(ProductEvent.OnFooterClicked(0, FooterType.MORE))
        advanceUntilIdle()

        viewModel.stateFlow.test {
            val updated = awaitItem()
            val updatedSection = updated.sections()!![0]
            assertTrue(updatedSection.visibleItemCount > testSection.visibleItemCount)
            assertTrue(updated.recentlyAddedImageUrl.isNotEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnNetworkRetryClicked 이벤트가 호출되면 fetchSections가 다시 호출된다`() = runTest {
        coEvery { getSectionsUseCase() } returns flowOf(emptyList())

        val viewModel = ProductViewModel(ProductState(), getSectionsUseCase)
        viewModel.onEvent(ProductEvent.OnNetworkRetryClicked)
        advanceUntilIdle()

        coVerify(exactly = 2) { getSectionsUseCase() } // init + retry
    }


    @Test
    fun `FooterType REFRESH 클릭 시 로딩 상태가 반영되고 콘텐츠가 셔플되며 이펙트가 발생한다`() = runTest {
        val sectionIndex = 0

        val originalSection = createTestSectionState(
            visibleCount = 10, totalCount = 10, productCount = 10
        )
        val originalIds =
            (originalSection.section.content as Content.GridContent).products.map { it.linkUrl }

        mockSections(getSectionsUseCase, originalSection)

        val viewModel = ProductViewModel(ProductState(), getSectionsUseCase)
        viewModel.onEvent(ProductEvent.OnFooterClicked(sectionIndex, FooterType.REFRESH))

        viewModel.stateFlow.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.sectionUiLoadingMap[sectionIndex])

            val refreshedState = awaitItem()
            assertEquals(false, refreshedState.sectionUiLoadingMap[sectionIndex])

            val refreshedSection = refreshedState.sections()?.get(sectionIndex)
            val shuffledProducts =
                (refreshedSection!!.section.content as Content.GridContent).products.map { it.linkUrl }

            assertEquals(originalIds.size, shuffledProducts.size)
            assertNotEquals(originalIds, shuffledProducts)
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.effect.test {
            val effect = awaitItem()
            assertEquals(ProductEffect.ShowRefreshSnackBar, effect)
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }
}