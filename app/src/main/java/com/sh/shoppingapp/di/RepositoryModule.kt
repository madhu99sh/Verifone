package com.sh.shoppingapp.di

import com.sh.shoppingapp.data.remote.ProductApi
import com.sh.shoppingapp.data.repository.ProductRepositoryImpl
import com.sh.shoppingapp.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providePro(api: ProductApi): ProductRepository =
        ProductRepositoryImpl(api)
}