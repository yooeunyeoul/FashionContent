package com.example.stylefeed.ui

import app.cash.turbine.test
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.test.MavericksTestRule
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.usecase.GetSectionsUseCase
import com.example.stylefeed.ui.viewmodel.ProductState
import com.example.stylefeed.ui.viewmodel.ProductViewModel
import com.example.stylefeed.utils.ApiException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductViewModelTest {

    private val getSectionsUseCase: GetSectionsUseCase = mockk(relaxed = true)

    @get:Rule
    val mavericksTestRule = MavericksTestRule()

    @Test
    fun `초기 상태에서 fetchSections 성공`() = runTest {
        // Given
        val sections = listOf(
            Section(header = null, content = Content.UnknownContent, footer = null)
        )

        coEvery { getSectionsUseCase() } returns flowOf(sections)

        // When
        val viewModel = ProductViewModel(ProductState(), getSectionsUseCase)

        // Then
        viewModel.stateFlow.test {
            val initialState = awaitItem()
            assertTrue(initialState.sections is Success)
            assertEquals(sections, initialState.sections.invoke())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `초기 상태에서 fetchSections 실패`() = runTest {
        // Given
        coEvery { getSectionsUseCase() } returns flow {
            throw ApiException(500, "서버 에러")
        }

        // When
        val viewModel = ProductViewModel(ProductState(), getSectionsUseCase)

        // Then
        viewModel.stateFlow.test {
            val state = awaitItem()
            assertTrue(state.sections is Fail)
            val error = (state.sections as Fail).error
            assertTrue(error is ApiException)
            assertEquals("서버 에러", error.message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}