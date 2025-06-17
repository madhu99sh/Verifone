package com.sh.shoppingapp.data.remote

import com.sh.shoppingapp.data.remote.model.PagedResult
import com.sh.shoppingapp.data.remote.model.ProductDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter

interface ProductApi {
    suspend fun searchProducts(query: String, limit: Int, skip: Int): PagedResult<ProductDto>
}

class ProductApiImpl(
    private val client: HttpClient, private val baseUrl: String
) : ProductApi {

    override suspend fun searchProducts(
        query: String, limit: Int, skip: Int
    ): PagedResult<ProductDto> {
        return client.get("$baseUrl/products/search") {
            parameter("q", query)
            parameter("limit", limit)
            parameter("skip", skip)
            headers {
                append(
                    "Cache-Control", "public, max-age=60, must-revalidate"
                )
            }
        }.body()
    }
}

