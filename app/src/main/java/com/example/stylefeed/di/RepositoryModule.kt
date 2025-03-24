package com.example.stylefeed.di

import com.example.stylefeed.domain.repository.ProductRepository
import com.example.stylefeed.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}