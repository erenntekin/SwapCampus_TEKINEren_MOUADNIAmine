package com.example.swapcampus_mamaabbas.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.shared.data.ListingRepository
import com.example.swapcampus_mamaabbas.shared.domain.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListingsViewModel(private val repository: ListingRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ListingsUiState>(ListingsUiState.Loading)
    val uiState: StateFlow<ListingsUiState> = _uiState.asStateFlow()

    private val _keyword = MutableStateFlow("")
    val keyword: StateFlow<String> = _keyword.asStateFlow()

    private val _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?> = _category.asStateFlow()

    init {
        refresh()
    }

    fun onKeywordChange(keyword: String) {
        _keyword.value = keyword
        refresh()
    }

    fun onCategoryChange(category: Category?) {
        _category.value = category
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = runCatching { repository.searchListings(_keyword.value, _category.value) }
                .fold(
                    onSuccess = { ListingsUiState.Success(it) },
                    onFailure = { ListingsUiState.Error(Res.string.error_load_listings) },
                )
        }
    }
}
