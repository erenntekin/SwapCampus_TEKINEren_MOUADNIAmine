package com.example.swapcampus_mamaabbas.shared.data

import com.example.swapcampus_mamaabbas.shared.domain.Category
import com.example.swapcampus_mamaabbas.shared.domain.ListingCatalog
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InMemoryListingRepositoryTest {

    private val repository = InMemoryListingRepository(ListingCatalog())

    @Test
    fun postListing_rejectsBlankTitle() = runTest {
        val result = repository.postListing("   ", "desc", 10.0, Category.BOOKS, null, authorId = 1)
        assertTrue(result.isFailure)
    }

    @Test
    fun postListing_rejectsNonPositivePrice() = runTest {
        val result = repository.postListing("Book", "desc", 0.0, Category.BOOKS, null, authorId = 1)
        assertTrue(result.isFailure)
    }

    @Test
    fun postListing_acceptsValidListing() = runTest {
        val before = repository.allListings().size
        val result = repository.postListing("Book", "desc", 10.0, Category.BOOKS, null, authorId = 1)
        assertTrue(result.isSuccess)
        assertEquals(before + 1, repository.allListings().size)
    }

    @Test
    fun updateListing_rejectsInvalidPrice() = runTest {
        val listing = repository.postListing("Book", "desc", 10.0, Category.BOOKS, null, authorId = 1).getOrThrow()
        val result = repository.updateListing(listing.id, "Book", "desc", -5.0, Category.BOOKS, null)
        assertTrue(result.isFailure)
    }
}
