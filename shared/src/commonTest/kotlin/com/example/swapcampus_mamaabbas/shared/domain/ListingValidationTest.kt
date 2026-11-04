package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ListingValidationTest {

    @Test
    fun validateListing_reportsEveryBrokenRule() {
        assertEquals(
            listOf(ListingError.BLANK_TITLE, ListingError.NON_POSITIVE_PRICE),
            validateListing(" ", -1.0),
        )
        assertTrue(validateListing("Bike", 10.0).isEmpty())
    }

    @Test
    fun listing_throwsTypedErrorOnInvalidValues() {
        val exception = assertFailsWith<InvalidListingException> {
            Listing(1, "", "", 5.0, Category.OTHER, null, 1, Available, LocalDateTime(2026, 10, 7, 10, 0))
        }
        assertEquals(listOf(ListingError.BLANK_TITLE), exception.errors)
    }

    @Test
    fun sellerStats_countsEachStatus() {
        val date = LocalDateTime(2026, 10, 7, 10, 0)
        val listings = listOf(
            Listing(1, "A", "", 5.0, Category.OTHER, null, 1, Available, date),
            Listing(2, "B", "", 5.0, Category.OTHER, null, 1, Reserved("bob"), date),
            Listing(3, "C", "", 5.0, Category.OTHER, null, 1, Sold(LocalDate(2026, 10, 7)), date),
            Listing(4, "D", "", 5.0, Category.OTHER, null, 1, Available, date),
        )
        assertEquals(SellerStats(posted = 4, available = 2, reserved = 1, sold = 1), listings.sellerStats())
    }
}
