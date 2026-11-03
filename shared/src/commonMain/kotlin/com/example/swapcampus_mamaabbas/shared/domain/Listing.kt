package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDateTime

data class Listing(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: Category,
    val photoUrl: String?,
    val authorId: Int,
    val status: ListingStatus,
    val creationDate: LocalDateTime,
) {
    init {
        validateListing(title, price).takeIf { it.isNotEmpty() }?.let { throw InvalidListingException(it) }
    }

    fun displayedUrlOrDefault(default: String): String = photoUrl ?: default
}
