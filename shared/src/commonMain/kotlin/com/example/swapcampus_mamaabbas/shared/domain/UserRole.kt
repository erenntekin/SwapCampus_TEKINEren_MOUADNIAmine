package com.example.swapcampus_mamaabbas.shared.domain

enum class UserRole {
    STUDENT,
    ADMINISTRATOR
}

val UserRole.canPost: Boolean
    get() = when (this) {
        UserRole.STUDENT -> true
        UserRole.ADMINISTRATOR -> false
    }