package com.example.stylefeed.di

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.example.stylefeed.ui.screens.product.viewmodel.ProductViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelModule {
    

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    fun productViewModelFactory(factory: ProductViewModel.Factory): AssistedViewModelFactory<*, *>
}