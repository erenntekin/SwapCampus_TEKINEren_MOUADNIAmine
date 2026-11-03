package com.example.swapcampus_mamaabbas.shared.domain

data class SellerStats(
    val posted: Int,
    val available: Int,
    val reserved: Int,
    val sold: Int,
)

fun List<Listing>.sellerStats(): SellerStats = SellerStats(
    posted = size,
    available = count { it.status == Available },
    reserved = count { it.status is Reserved },
    sold = count { it.status is Sold },
)
