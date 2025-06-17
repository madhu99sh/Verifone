package com.sh.shoppingapp.domain.model

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val thumbnail: String
)