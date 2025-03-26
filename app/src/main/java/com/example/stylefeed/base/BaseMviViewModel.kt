package com.example.stylefeed.base

import com.airbnb.mvrx.MavericksViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


abstract class BaseMviViewModel<S : BaseState, E : BaseUiEvent, F : BaseUiEffect>(
    initialState: S
) : MavericksViewModel<S>(initialState) {

    private val _effect = Channel<F>()
    val effect = _effect.receiveAsFlow()

    protected fun sendEffect(effect: F) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    abstract fun onEvent(event: E)
}
