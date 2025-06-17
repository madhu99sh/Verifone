package com.sh.shoppingapp.data.repository

import com.sh.shoppingapp.data.remote.ProductApi
import com.sh.shoppingapp.data.remote.model.PagedResult
import com.sh.shoppingapp.domain.model.Product
import com.sh.shoppingapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun searchProducts(
        query: String,
        limit: Int,
        skip: Int,
    ): PagedResult<Product> {
        val response = api.searchProducts(query, limit, skip)
        val products = response.products.map { dto ->
            Product(
                id = dto.id,
                name = dto.title,
                category = dto.category,
                price = dto.price,
                thumbnail = dto.thumbnail
            )
        }
        return PagedResult(
            products = products,
            total = response.total,
            skip = response.skip,
            limit = response.limit
        )
    }

}


