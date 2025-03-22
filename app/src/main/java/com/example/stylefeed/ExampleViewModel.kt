package com.example.stylefeed

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.example.stylefeed.base.BaseMviViewModel
import com.example.stylefeed.base.UiEffect
import com.example.stylefeed.base.UiEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

data class ExampleState(
    val data: String = "",
) : MavericksState

sealed class CounterEvent : UiEvent {
    object Increment : CounterEvent()
}

sealed class CounterEffect : UiEffect {
    object ShowToast : CounterEffect()
}

class ExampleViewModel @AssistedInject constructor(
    @Assisted initialState: ExampleState,
) : BaseMviViewModel<ExampleState,CounterEvent,CounterEffect>(initialState) {

    fun increat() {
        setState {
            copy(data = data + "a")
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ExampleViewModel, ExampleState> {
        override fun create(state: ExampleState): ExampleViewModel
    }

    companion object : MavericksViewModelFactory<ExampleViewModel, ExampleState> by hiltMavericksViewModelFactory()

    override fun onEvent(event: CounterEvent) {

    }


}
