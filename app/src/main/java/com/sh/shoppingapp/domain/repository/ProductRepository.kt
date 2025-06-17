package com.sh.shoppingapp.domain.repository

import com.sh.shoppingapp.data.remote.model.PagedResult
import com.sh.shoppingapp.domain.model.Product

interface ProductRepository {
    suspend fun searchProducts(
        query: String, limit: Int, skip: Int
    ): PagedResult<Product>
}
