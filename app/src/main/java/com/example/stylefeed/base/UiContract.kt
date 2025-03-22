package com.example.stylefeed.base


interface UiEvent
interface UiEffect

object NoEvent : UiEvent
object NoEffect : UiEffect

typealias BaseMviViewModelNoEF<S> = BaseMviViewModel<S, NoEvent, NoEffect>


