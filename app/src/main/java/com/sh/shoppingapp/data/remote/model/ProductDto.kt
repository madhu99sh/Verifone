package com.sh.shoppingapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("category") val category: String,
    @SerialName("price") val price: Double,
    @SerialName("thumbnail") val thumbnail: String,
)
