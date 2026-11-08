package com.example.swapcampus_mamaabbas.composeapp.navigation

import kotlinx.serialization.Serializable

@Serializable object ListingsRoute
@Serializable object MyListingsRoute
@Serializable object ProfileRoute
@Serializable object LoginRoute
@Serializable object PostRoute
@Serializable data class DetailRoute(val id: Int)
@Serializable data class EditRoute(val id: Int)
