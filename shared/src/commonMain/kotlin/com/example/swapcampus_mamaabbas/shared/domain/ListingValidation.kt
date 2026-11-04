package com.example.swapcampus_mamaabbas.shared.domain

enum class ListingError {
    BLANK_TITLE,
    NON_POSITIVE_PRICE,
}

class InvalidListingException(val errors: List<ListingError>) :
    IllegalArgumentException("Invalid listing: $errors")

fun validateListing(title: String, price: Double): List<ListingError> = buildList {
    if (title.isBlank()) add(ListingError.BLANK_TITLE)
    if (price <= 0) add(ListingError.NON_POSITIVE_PRICE)
}
