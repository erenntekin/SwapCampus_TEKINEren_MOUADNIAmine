package com.example.swapcampus_mamaabbas.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapcampus_mamaabbas.shared.data.AuthRepository
import com.example.swapcampus_mamaabbas.shared.data.ListingRepository
import com.example.swapcampus_mamaabbas.shared.domain.Available
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import com.example.swapcampus_mamaabbas.shared.domain.ListingStatus
import com.example.swapcampus_mamaabbas.shared.domain.Reserved
import com.example.swapcampus_mamaabbas.shared.domain.Sold
import com.example.swapcampus_mamaabbas.shared.domain.UserAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

class DetailViewModel(
    private val repository: ListingRepository,
    private val auth: AuthRepository,
) : ViewModel() {

    private val _listing = MutableStateFlow<Listing?>(null)
    val listing: StateFlow<Listing?> = _listing.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _seller = MutableStateFlow<UserAccount?>(null)
    val seller: StateFlow<UserAccount?> = _seller.asStateFlow()

    val isOwner: StateFlow<Boolean> = combine(_listing, auth.currentUser) { listing, user ->
        listing != null && user != null && listing.authorId == user.id
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun load(id: Int) {
        viewModelScope.launch {
            val listing = repository.findListing(id)
            _listing.value = listing
            _seller.value = listing?.let { auth.findAccount(it.authorId) }
            _isLoading.value = false
        }
    }

    fun reserve(buyer: String) {
        if (buyer.isNotBlank()) changeStatus(Reserved(buyer.trim()))
    }

    fun markSold() = changeStatus(Sold(Clock.System.todayIn(TimeZone.currentSystemDefault())))

    fun makeAvailable() = changeStatus(Available)

    fun remove(onRemoved: () -> Unit) {
        val current = _listing.value?.takeIf { isOwner.value } ?: return
        viewModelScope.launch {
            if (repository.removeListing(current.id)) onRemoved()
        }
    }

    private fun changeStatus(newStatus: ListingStatus) {
        val current = _listing.value?.takeIf { isOwner.value } ?: return
        viewModelScope.launch {
            repository.changeStatus(current.id, newStatus)?.let { _listing.value = it }
        }
    }
}
