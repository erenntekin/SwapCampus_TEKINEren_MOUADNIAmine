package com.example.swapcampus_mamaabbas.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.shared.data.AuthRepository
import com.example.swapcampus_mamaabbas.shared.data.ListingRepository
import com.example.swapcampus_mamaabbas.shared.domain.Category
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import com.example.swapcampus_mamaabbas.shared.domain.ListingError
import com.example.swapcampus_mamaabbas.shared.domain.canPost
import com.example.swapcampus_mamaabbas.shared.domain.validateListing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

data class FormErrors(
    val title: StringResource? = null,
    val price: StringResource? = null,
    val general: StringResource? = null,
)

class ListingFormViewModel(
    private val repository: ListingRepository,
    private val auth: AuthRepository,
) : ViewModel() {

    private var editingId: Int? = null

    private val _initial = MutableStateFlow<Listing?>(null)
    val initial: StateFlow<Listing?> = _initial.asStateFlow()

    private val _errors = MutableStateFlow(FormErrors())
    val errors: StateFlow<FormErrors> = _errors.asStateFlow()

    fun load(id: Int?) {
        editingId = id
        if (id == null) return
        viewModelScope.launch {
            val listing = repository.findListing(id)
            if (listing != null && listing.authorId == auth.currentUser.value?.id) {
                _initial.value = listing
            } else {
                _errors.value = FormErrors(general = Res.string.error_not_owner)
            }
        }
    }

    fun save(
        title: String,
        description: String,
        priceText: String,
        category: Category,
        photoUrl: String?,
        onSaved: () -> Unit,
    ) {
        val user = auth.currentUser.value ?: return fail(Res.string.error_login_required)
        if (!user.role.canPost) return fail(Res.string.error_admin_cannot_post)
        if (editingId != null && _initial.value == null) return fail(Res.string.error_not_owner)

        val price = priceText.replace(',', '.').trim().toDoubleOrNull()
        val domainErrors = validateListing(title, price ?: 0.0)
        val errors = FormErrors(
            title = Res.string.error_title_blank.takeIf { ListingError.BLANK_TITLE in domainErrors },
            price = when {
                price == null -> Res.string.error_price_not_number
                ListingError.NON_POSITIVE_PRICE in domainErrors -> Res.string.error_price_not_positive
                else -> null
            },
        )
        _errors.value = errors
        if (errors.title != null || errors.price != null || price == null) return

        viewModelScope.launch {
            val result = _initial.value?.let {
                repository.updateListing(it.id, title, description, price, category, photoUrl)
            } ?: repository.postListing(title, description, price, category, photoUrl, user.id)

            result
                .onSuccess {
                    _errors.value = FormErrors()
                    onSaved()
                }
                .onFailure { fail(Res.string.error_save_failed) }
        }
    }

    private fun fail(message: StringResource) {
        _errors.value = FormErrors(general = message)
    }
}
