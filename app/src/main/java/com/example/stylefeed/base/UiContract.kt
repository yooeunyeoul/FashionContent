package com.example.stylefeed.base


interface UiEvent
interface UiEffect

object NoEvent : UiEvent
object NoEffect : UiEffect

sealed class CounterEvent : UiEvent {
    object Increment : CounterEvent()
}

sealed class CounterEffect : UiEffect {
    object ShowToast : CounterEffect()
}

typealias BaseMviViewModelNoEF<S> = BaseMviViewModel<S, NoEvent, NoEffect>


