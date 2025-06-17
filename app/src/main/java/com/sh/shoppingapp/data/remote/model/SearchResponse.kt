package com.sh.shoppingapp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PagedResult<T>(
    val products: List<T>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
