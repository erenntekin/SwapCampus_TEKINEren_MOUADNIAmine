package com.example.swapcampus_mamaabbas.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapcampus_mamaabbas.shared.data.AuthRepository
import com.example.swapcampus_mamaabbas.shared.data.ListingRepository
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import com.example.swapcampus_mamaabbas.shared.domain.bySeller
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyListingsViewModel(
    private val repository: ListingRepository,
    private val auth: AuthRepository,
) : ViewModel() {

    private val _listings = MutableStateFlow<List<Listing>>(emptyList())
    val listings: StateFlow<List<Listing>> = _listings.asStateFlow()

    fun refresh() {
        val user = auth.currentUser.value ?: return
        viewModelScope.launch {
            _listings.value = repository.allListings().bySeller(user.id)
        }
    }
}
