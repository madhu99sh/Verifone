package com.sh.shoppingapp.presenter

import com.sh.shoppingapp.domain.model.Product

sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()
    data class Success(val products: List<Product>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
