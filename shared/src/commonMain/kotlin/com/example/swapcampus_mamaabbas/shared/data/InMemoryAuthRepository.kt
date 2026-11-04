package com.example.swapcampus_mamaabbas.shared.data

import com.example.swapcampus_mamaabbas.shared.domain.UserAccount
import com.example.swapcampus_mamaabbas.shared.domain.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDateTime

class InMemoryAuthRepository : AuthRepository {

    private val accounts = listOf(
        UserAccount(1, "Alice Martin", "alice@isep.fr", "1234", LocalDateTime(2026, 9, 1, 0, 0), UserRole.STUDENT),
        UserAccount(2, "Bob Durand", "bob@isep.fr", "1234", LocalDateTime(2026, 9, 1, 0, 0), UserRole.STUDENT),
        UserAccount(3, "Admin ISEP", "admin@isep.fr", "admin", LocalDateTime(2026, 9, 1, 0, 0), UserRole.ADMINISTRATOR),
    )

    private val _currentUser = MutableStateFlow<UserAccount?>(null)
    override val currentUser: StateFlow<UserAccount?> = _currentUser.asStateFlow()

    override fun authenticate(email: String, password: String): UserAccount? {

        val account = accounts.find {
            it.email.equals(email.trim(), ignoreCase = true) && it.passwordHash == password && it.active
        }
        if (account != null) {
            _currentUser.value = account
            return account
        }
        return null
    }

    override fun logout() {
        _currentUser.value = null
    }

    override fun findAccount(id: Int): UserAccount? = accounts.find { it.id == id }
}
