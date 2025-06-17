package com.sh.shoppingapp.di

import com.sh.shoppingapp.domain.repository.ProductRepository
import com.sh.shoppingapp.domain.usecase.PerformSearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providePerformSearchUseCase(repository: ProductRepository): PerformSearchUseCase =
        PerformSearchUseCase(repository)
}