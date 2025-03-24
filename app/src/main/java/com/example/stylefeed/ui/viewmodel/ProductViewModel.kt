package com.example.stylefeed.ui.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.example.stylefeed.base.BaseMviViewModel
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.model.FooterType
import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.usecase.GetSectionsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

data class ProductState(
    val sections: Async<List<SectionState>> = Uninitialized
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

            when (footerType) {
                FooterType.MORE -> {
                    val newVisibleCount = (sectionState.visibleItemCount + 3).coerceAtMost(sectionState.totalItemCount)
                    updatedSections[sectionIndex] = sectionState.copy(visibleItemCount = newVisibleCount)
                }

                FooterType.REFRESH -> {
                    // 여기서 shuffled 호출 (setState 외부에서!)
                    val shuffledContent = sectionState.section.content.shuffleContent()
                    val updatedSection = sectionState.section.copy(content = shuffledContent)
                    updatedSections[sectionIndex] = sectionState.copy(section = updatedSection)
                }

                else -> Unit
            }

            // 최종 setState는 shuffle 등 랜덤성 작업 이후 결과값을 넣어주므로 순수함수 조건 충족
            setState {
                copy(sections = Success(updatedSections))
            }
        }
    }

}
private fun Content.shuffleContent(): Content = when (this) {
    is Content.GridContent -> copy(products = products.shuffled())
    is Content.ScrollContent -> copy(products = products.shuffled())
    is Content.BannerContent -> copy(banners = banners.shuffled())
    is Content.StyleContent -> copy(styles = styles.shuffled())
    else -> this
}


