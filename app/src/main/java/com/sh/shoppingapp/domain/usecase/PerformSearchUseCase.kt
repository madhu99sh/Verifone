package com.sh.shoppingapp.domain.usecase

import com.sh.shoppingapp.data.remote.model.PagedResult
import com.sh.shoppingapp.domain.ApiResult
import com.sh.shoppingapp.domain.model.Product
import com.sh.shoppingapp.domain.repository.ProductRepository
import javax.inject.Inject


class PerformSearchUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(
        query: String, limit: Int, skip: Int
    ): ApiResult<PagedResult<Product>> {
        return try {
            val products = repository.searchProducts(query, limit, skip)
            ApiResult.Success(products)
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}
