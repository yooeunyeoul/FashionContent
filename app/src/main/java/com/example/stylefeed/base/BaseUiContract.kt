package com.example.stylefeed.base


interface BaseUiEvent
interface BaseUiEffect

object NoEvent : BaseUiEvent
object NoEffect : BaseUiEffect

typealias BaseMviViewModelNoEF<S> = BaseMviViewModel<S, NoEvent, NoEffect>


