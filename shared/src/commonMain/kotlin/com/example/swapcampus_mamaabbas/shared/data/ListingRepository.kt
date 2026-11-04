package com.example.swapcampus_mamaabbas.shared.data

import com.example.swapcampus_mamaabbas.shared.domain.Category
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import com.example.swapcampus_mamaabbas.shared.domain.ListingStatus

interface ListingRepository {
    suspend fun allListings(): List<Listing>
    suspend fun searchListings(keyword: String? = null, category: Category? = null): List<Listing>
    suspend fun postListing(
        title: String,
        description: String,
        price: Double,
        category: Category,
        photoUrl: String?,
        authorId: Int
    ): Result<Listing>

    suspend fun updateListing(
        id: Int,
        title: String,
        description: String,
        price: Double,
        category: Category,
        photoUrl: String?
    ): Result<Listing>

    suspend fun changeStatus(id: Int, newStatus: ListingStatus): Listing?
    suspend fun findListing(id: Int): Listing?
    suspend fun removeListing(id: Int): Boolean
}