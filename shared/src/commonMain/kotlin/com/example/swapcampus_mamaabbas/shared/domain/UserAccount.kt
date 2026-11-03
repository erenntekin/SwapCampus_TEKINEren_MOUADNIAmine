package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDateTime

data class UserAccount(
    val id: Int,
    val name: String,
    val email: String,
    val passwordHash: String,
    val creationDate: LocalDateTime,
    val role: UserRole,
    val active: Boolean = true,
)
