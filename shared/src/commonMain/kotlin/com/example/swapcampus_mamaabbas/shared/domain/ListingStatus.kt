package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDate

sealed class ListingStatus

data object Available : ListingStatus()
data class Reserved(val buyer: String) : ListingStatus()
data class Sold(val saleDate: LocalDate) : ListingStatus()

val ListingStatus.isVisibleInSearch: Boolean
    get() = when (this) {
        Available -> true
        is Reserved -> false
        is Sold -> false
    }

fun ListingStatus.canTransitionTo(newStatus: ListingStatus): Boolean = when (this) {
    Available -> newStatus is Reserved
    is Reserved -> newStatus is Sold || newStatus == Available
    is Sold -> newStatus == Available
}
