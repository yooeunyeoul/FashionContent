package com.example.stylefeed.ui.screens.product.viewmodel

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.example.stylefeed.base.BaseMviViewModel
import com.example.stylefeed.domain.model.ApiError
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.usecase.GetSectionsUseCase
import com.example.stylefeed.ui.screens.product.interactions.updateSectionForMore
import com.example.stylefeed.ui.screens.product.interactions.updateSectionForRefresh
import com.example.stylefeed.utils.ApiException
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ProductViewModel @AssistedInject constructor(
    @Assisted initialState: ProductState,
    private val getSectionsUseCase: GetSectionsUseCase,
) : BaseMviViewModel<ProductState, ProductEvent, ProductEffect>(initialState) {

    init {
        fetchSections()
    }

    private fun fetchSections() {
        getSectionsUseCase()
            .execute { asyncResult ->
                when (asyncResult) {
                    is Success -> copy(sections = asyncResult, apiError = null)

                    is Fail -> {
                        val apiException = asyncResult.error as? ApiException
                        val apiError =
                            ApiError.fromCode(apiException?.code ?: ApiError.NETWORK_ERROR_CODE)
                        copy(sections = asyncResult, apiError = apiError)
                    }

                    else -> copy(sections = asyncResult)
                }
            }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ProductViewModel, ProductState> {
        override fun create(state: ProductState): ProductViewModel
    }

    companion object :
        MavericksViewModelFactory<ProductViewModel, ProductState> by hiltMavericksViewModelFactory()

    override fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.OnFooterClicked -> handleFooterEvent(
                event.sectionIndex,
                event.footerType
            )

            ProductEvent.OnNetworkRetryClicked -> {
                fetchSections()
            }
        }
    }

    private fun handleFooterEvent(sectionIndex: Int, footerType: FooterType) {
        withState { state ->
            val currentSections = state.sections() ?: return@withState

            when (footerType) {
                FooterType.MORE -> {
                    val (updatedSections, addedIds) = updateSectionForMore(
                        currentSections,
                        sectionIndex
                    )
                    setState {
                        copy(sections = Success(updatedSections), recentlyAddedImageUrl = addedIds)
                    }
                }

                FooterType.REFRESH -> {
                    val updatedSections = updateSectionForRefresh(currentSections, sectionIndex)
                    setState {
                        copy(sections = Success(updatedSections))
                    }
                }

                else -> Unit
            }
        }
    }
}

