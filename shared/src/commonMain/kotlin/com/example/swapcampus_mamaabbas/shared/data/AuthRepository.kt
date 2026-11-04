package com.example.swapcampus_mamaabbas.shared.data

import com.example.swapcampus_mamaabbas.shared.domain.UserAccount
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: StateFlow<UserAccount?>
    fun authenticate(email: String, password: String): UserAccount?
    fun logout()
    fun findAccount(id: Int): UserAccount?
}
