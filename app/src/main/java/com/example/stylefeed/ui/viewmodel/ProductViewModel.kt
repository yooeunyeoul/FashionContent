package com.example.stylefeed.ui.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.example.stylefeed.base.BaseMviViewModel
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.model.getItemIds
import com.example.stylefeed.domain.model.shuffleContent
import com.example.stylefeed.domain.usecase.GetSectionsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

data class ProductState(
    val sections: Async<List<SectionState>> = Uninitialized,
    val recentlyAddedImageUrl: Set<String> = emptySet() // 최근 추가된 섹션 별 아이템 index
) : MavericksState

class ProductViewModel @AssistedInject constructor(
    @Assisted initialState: ProductState,
    private val getSectionsUseCase: GetSectionsUseCase,
) : BaseMviViewModel<ProductState, ProductEvent, ProductEffect>(initialState) {

    init {
        fetchSections()
    }


    private fun fetchSections() {
        getSectionsUseCase()
            .execute { copy(sections = it) } // 결과를 state의 sections에 저장
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
        }
    }

    private fun handleFooterEvent(sectionIndex: Int, footerType: FooterType) {
        withState { currentState ->
            val updatedSections = currentState.sections()?.toMutableList() ?: return@withState
            val sectionState = updatedSections[sectionIndex]

            var addedIds = emptySet<String>()

            when (footerType) {
                FooterType.MORE -> {
                    val oldVisibleContentIds = sectionState.visibleContent.getItemIds()

                    val newVisibleCount = (sectionState.visibleItemCount + 3)
                        .coerceAtMost(sectionState.totalItemCount)

                    // ✅ 여기서 visibleItemCount만 늘려서 visibleContent가 업데이트되도록 유도
                    val updatedSectionState = sectionState.copy(visibleItemCount = newVisibleCount)
                    val newVisibleContentIds = updatedSectionState.visibleContent.getItemIds()

                    addedIds = newVisibleContentIds - oldVisibleContentIds // 새로 추가된 아이템 ID
                    updatedSections[sectionIndex] = updatedSectionState
                }

                FooterType.REFRESH -> {
                    val shuffledContent = sectionState.section.content.shuffleContent()
                    updatedSections[sectionIndex] = sectionState.copy(
                        section = sectionState.section.copy(content = shuffledContent)
                    )
                }

                else -> Unit
            }

            setState {
                copy(
                    sections = Success(updatedSections),
                    recentlyAddedImageUrl = addedIds // ✅ 올바르게 추가된 아이템 ID를 저장
                )
            }
        }
    }
}

