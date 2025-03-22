package com.example.stylefeed.ui.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.example.stylefeed.CounterEffect
import com.example.stylefeed.CounterEvent
import com.example.stylefeed.base.BaseMviViewModel
import com.example.stylefeed.domain.model.Section
import com.example.stylefeed.domain.repository.ProductRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

data class ProductState(
    val sections: Async<List<Section>> = Uninitialized,
) : MavericksState

class ProductViewModel @AssistedInject constructor(
    @Assisted initialState: ProductState,
    private val repository: ProductRepository,
) : BaseMviViewModel<ProductState, CounterEvent, CounterEffect>(initialState) {

    init {
        fetchSections()
    }

    private fun fetchSections() {
        repository.getSections()
            .execute { copy(sections = it) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ProductViewModel, ProductState> {
        override fun create(state: ProductState): ProductViewModel
    }

    companion object :
        MavericksViewModelFactory<ProductViewModel, ProductState> by hiltMavericksViewModelFactory()

    override fun onEvent(event: CounterEvent) {

    }


}
