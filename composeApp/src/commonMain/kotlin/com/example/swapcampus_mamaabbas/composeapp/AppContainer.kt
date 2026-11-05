package com.example.swapcampus_mamaabbas.composeapp

import com.example.swapcampus_mamaabbas.shared.data.AuthRepository
import com.example.swapcampus_mamaabbas.shared.data.InMemoryAuthRepository
import com.example.swapcampus_mamaabbas.shared.data.InMemoryListingRepository
import com.example.swapcampus_mamaabbas.shared.data.ListingRepository

object AppContainer {
    val listingRepository: ListingRepository by lazy { InMemoryListingRepository() }
    val authRepository: AuthRepository by lazy { InMemoryAuthRepository() }
}
