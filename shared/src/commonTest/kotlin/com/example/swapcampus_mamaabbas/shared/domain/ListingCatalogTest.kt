package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListingCatalogTest {

    private val catalog = ListingCatalog(now = { LocalDateTime(2026, 10, 7, 10, 0) })

    private fun post(title: String = "Calculator", price: Double = 20.0, category: Category = Category.ELECTRONICS) =
        catalog.postListing(title, "TI-83 in good condition", price, category, photoUrl = null, authorId = 1)

    @Test
    fun postListing_initialStatusIsAvailable() {
        val listing = post()
        assertEquals(Available, listing.status)
        assertEquals(listOf(listing), catalog.allListings())
    }

    @Test
    fun postListing_rejectsBlankTitleOrZeroPrice() {
        assertFailsWith<IllegalArgumentException> { post(title = "  ") }
        assertFailsWith<IllegalArgumentException> { post(price = 0.0) }
        assertTrue(catalog.allListings().isEmpty())
    }

    @Test
    fun searchListings_combinesKeywordAndCategory() {
        val calculator = post()
        post(title = "Calculus textbook", category = Category.BOOKS)
        assertEquals(listOf(calculator), catalog.searchListings(keyword = "calculator"))
        assertEquals(listOf(calculator), catalog.searchListings(category = Category.ELECTRONICS))
        assertTrue(catalog.searchListings(keyword = "calculator", category = Category.BOOKS).isEmpty())
    }

    @Test
    fun changeStatus_followsAllowedTransitions() {
        val listing = post()
        assertNull(catalog.changeStatus(listing.id, Sold(LocalDate(2026, 10, 7))))
        assertEquals(Reserved("bob"), catalog.changeStatus(listing.id, Reserved("bob"))?.status)
        assertTrue(catalog.searchListings().isEmpty())
    }

    @Test
    fun removeListing_deletesPermanently() {
        val listing = post()
        assertTrue(catalog.removeListing(listing.id))
        assertNull(catalog.findListing(listing.id))
    }
}
