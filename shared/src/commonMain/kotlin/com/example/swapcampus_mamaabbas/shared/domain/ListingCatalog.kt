package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun List<Listing>.available(): List<Listing> = filter { it.status.isVisibleInSearch }

fun List<Listing>.bySeller(authorId: Int): List<Listing> = filter { it.authorId == authorId }

class ListingCatalog(
    private val now: () -> LocalDateTime = {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    },
) {
    private val listings = mutableListOf<Listing>()
    private var nextId = 1

    fun allListings(): List<Listing> = listings.toList()

    fun findListing(id: Int): Listing? = listings.find { it.id == id }

    fun postListing(
        title: String,
        description: String,
        price: Double,
        category: Category,
        photoUrl: String?,
        authorId: Int,
    ): Listing = Listing(
        id = nextId,
        title = title.trim(),
        description = description.trim(),
        price = price,
        category = category,
        photoUrl = photoUrl?.trim()?.takeIf { it.isNotEmpty() },
        authorId = authorId,
        status = Available,
        creationDate = now(),
    ).also {
        listings += it
        nextId++
    }

    fun searchListings(
        keyword: String? = null,
        category: Category? = null,
    ): List<Listing> = listings.available().filter { listing ->
        (category == null || listing.category == category) &&
            (keyword.isNullOrBlank() ||
                listing.title.contains(keyword.trim(), ignoreCase = true) ||
                listing.description.contains(keyword.trim(), ignoreCase = true))
    }

    fun updateListing(
        id: Int,
        title: String,
        description: String,
        price: Double,
        category: Category,
        photoUrl: String?,
    ): Listing? {
        val current = findListing(id) ?: return null
        if (current.status is Sold) return null
        return replace(
            current.copy(
                title = title.trim(),
                description = description.trim(),
                price = price,
                category = category,
                photoUrl = photoUrl?.trim()?.takeIf { it.isNotEmpty() },
            )
        )
    }

    fun repostListing(id: Int): Listing? {
        val sold = findListing(id)?.takeIf { it.status is Sold } ?: return null
        return sold.copy(id = nextId, status = Available, creationDate = now())
            .also {
                listings += it
                nextId++
            }
    }

    fun changeStatus(id: Int, newStatus: ListingStatus): Listing? {
        val current = findListing(id) ?: return null
        if (!current.status.canTransitionTo(newStatus)) return null
        return replace(current.copy(status = newStatus))
    }

    fun removeListing(id: Int): Boolean = listings.removeAll { it.id == id }

    private fun replace(listing: Listing): Listing = listing.also { updated ->
        listings[listings.indexOfFirst { it.id == updated.id }] = updated
    }
}
