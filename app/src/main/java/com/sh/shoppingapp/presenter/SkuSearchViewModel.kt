package com.sh.shoppingapp.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sh.shoppingapp.domain.ApiResult
import com.sh.shoppingapp.domain.usecase.PerformSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SkuSearchViewModel @Inject constructor(
    private val performSearchUseCase: PerformSearchUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    //    private var currentQuery: String = ""
    private var currentSkip = 0
    private var totalCount = 0
    private val pageSize = 10
    private var isLoadingMore = false

    private val _canLoadMore = MutableStateFlow(true)
    val canLoadMore: StateFlow<Boolean> = _canLoadMore.asStateFlow()

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
        currentSkip = 0
        totalCount = 0
        search()
    }

    fun loadNextPage() {
        if (isLoadingMore || !_canLoadMore.value) return

        isLoadingMore = true
        viewModelScope.launch {
            when (val result = performSearchUseCase(searchQuery.value, pageSize, currentSkip)) {
                is ApiResult.Success -> {
                    val paged = result.data
                    currentSkip += paged.products.size
                    totalCount = paged.total

                    val currentList =
                        (uiState.value as? SearchUiState.Success)?.products ?: emptyList()
                    _uiState.value = SearchUiState.Success(currentList + paged.products)

                    _canLoadMore.value = currentSkip < totalCount
                }

                is ApiResult.Error -> {
                    _uiState.value =
                        SearchUiState.Error(result.exception.localizedMessage ?: "Load failed")
                    _canLoadMore.value = false
                }
            }
            isLoadingMore = false
        }
    }


    private fun search() {
        _uiState.value = SearchUiState.Loading
        _canLoadMore.value = true
        currentSkip = 0
        totalCount = 0

        viewModelScope.launch {
            when (val result = performSearchUseCase(searchQuery.value, pageSize, 0)) {
                is ApiResult.Success -> {
                    val paged = result.data
                    currentSkip = paged.products.size
                    totalCount = paged.total
                    _uiState.value = SearchUiState.Success(paged.products)
                    _canLoadMore.value = currentSkip < totalCount
                }

                is ApiResult.Error -> {
                    _uiState.value =
                        SearchUiState.Error(result.exception.localizedMessage ?: "Search failed")
                    _canLoadMore.value = false
                }
            }
        }
    }


}
