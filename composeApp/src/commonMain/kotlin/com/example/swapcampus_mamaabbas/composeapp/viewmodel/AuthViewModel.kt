package com.example.swapcampus_mamaabbas.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.shared.data.AuthRepository
import com.example.swapcampus_mamaabbas.shared.domain.UserAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.StringResource

class AuthViewModel(private val auth: AuthRepository) : ViewModel() {

    val currentUser: StateFlow<UserAccount?> = auth.currentUser

    private val _error = MutableStateFlow<StringResource?>(null)
    val error: StateFlow<StringResource?> = _error.asStateFlow()

    fun login(email: String, password: String): Boolean {
        val user = auth.authenticate(email, password)
        _error.value = if (user == null) Res.string.error_invalid_credentials else null
        return user != null
    }

    fun clearError() {
        _error.value = null
    }

    fun logout() {
        auth.logout()
    }
}
