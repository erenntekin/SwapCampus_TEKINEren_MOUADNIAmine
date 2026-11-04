package com.example.swapcampus_mamaabbas.shared.data

import com.example.swapcampus_mamaabbas.shared.domain.Category
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import com.example.swapcampus_mamaabbas.shared.domain.ListingCatalog
import com.example.swapcampus_mamaabbas.shared.domain.ListingStatus
import com.example.swapcampus_mamaabbas.shared.domain.Reserved

class InMemoryListingRepository(private val catalog: ListingCatalog = ListingCatalog()): ListingRepository{

    init {
        catalog.postListing("Calculatrice TI-83", "Parfait état, piles fournies", 25.0, Category.ELECTRONICS, null, authorId = 1)
        catalog.postListing("Manuel d'analyse L1", "Quelques annotations au crayon", 15.0, Category.BOOKS, null, authorId = 1)
        catalog.postListing("Vélo de ville", "Taille M, révisé en septembre", 80.0, Category.SPORTS_LEISURE, null, authorId = 2)
        val desk = catalog.postListing("Bureau compact", "120 x 60 cm, à démonter sur place", 40.0, Category.FURNITURE, null, authorId = 2)
        catalog.changeStatus(desk.id, Reserved(buyer = "alice@isep.fr"))
    }


    override suspend fun allListings(): List<Listing> {
        return catalog.allListings()
    }

    override suspend fun searchListings(keyword: String?, category: Category?): List<Listing> {
        return catalog.searchListings(keyword, category)
    }

    override suspend fun postListing(
        title: String,
        description: String,
        price: Double,
        category: Category,
        photoUrl: String?,
        authorId: Int
    ): Result<Listing> {
        return runCatching { catalog.postListing(title, description, price, category, photoUrl, authorId) }
    }

    override suspend fun updateListing(
        id: Int,
        title: String,
        description: String,
        price: Double,
        category: Category,
        photoUrl: String?
    ): Result<Listing> {
        return runCatching { catalog.updateListing(id, title, description, price, category, photoUrl)?:error("Listing not found or already sold") }
    }

    override suspend fun changeStatus(id: Int, newStatus: ListingStatus): Listing? {
        return catalog.changeStatus(id, newStatus)
    }

    override suspend fun findListing(id: Int): Listing? {
        return catalog.findListing(id)
    }

    override suspend fun removeListing(id: Int): Boolean {
        return catalog.removeListing(id)
    }

}