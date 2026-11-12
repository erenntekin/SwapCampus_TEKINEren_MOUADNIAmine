package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ListingStatusTest {

    private val sold = Sold(LocalDate(2026, 11, 18))

    // Exercise 1 (solution)
    @Test
    fun canTransitionTo_followsTheAllowedTransitions() {
        assertFalse(Available.canTransitionTo(sold))
        assertTrue(sold.canTransitionTo(Available))
    }
}
