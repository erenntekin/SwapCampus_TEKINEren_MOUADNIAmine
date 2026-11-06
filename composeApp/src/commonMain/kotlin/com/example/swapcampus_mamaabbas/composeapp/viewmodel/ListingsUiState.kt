package com.example.swapcampus_mamaabbas.composeapp.viewmodel

import com.example.swapcampus_mamaabbas.shared.domain.Listing
import org.jetbrains.compose.resources.StringResource

sealed interface ListingsUiState {
    data object Loading : ListingsUiState
    data class Success(val listings: List<Listing>) : ListingsUiState
    data class Error(val messageRes: StringResource) : ListingsUiState
}
