package com.example.swapcampus_mamaabbas.composeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.composeapp.ui.components.EmptyState
import com.example.swapcampus_mamaabbas.composeapp.ui.components.ListingImage
import com.example.swapcampus_mamaabbas.composeapp.ui.components.StatusTag
import com.example.swapcampus_mamaabbas.composeapp.ui.components.labelRes
import com.example.swapcampus_mamaabbas.composeapp.ui.i18n.currentLanguageTag
import com.example.swapcampus_mamaabbas.composeapp.ui.i18n.formatDate
import com.example.swapcampus_mamaabbas.composeapp.ui.i18n.formatPrice
import com.example.swapcampus_mamaabbas.shared.domain.Available
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import com.example.swapcampus_mamaabbas.shared.domain.Reserved
import com.example.swapcampus_mamaabbas.shared.domain.Sold
import com.example.swapcampus_mamaabbas.shared.domain.UserAccount
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    listing: Listing?,
    isLoading: Boolean,
    seller: UserAccount?,
    isOwner: Boolean,
    onReserve: (buyer: String) -> Unit,
    onMarkSold: () -> Unit,
    onMakeAvailable: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit,
) {
    var showReserveDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.back))
                    }
                },
            )
        },
    ) { padding ->
        when {
            isLoading -> Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            listing == null -> EmptyState(
                icon = Icons.Outlined.SearchOff,
                title = stringResource(Res.string.listing_not_found_title),
                message = stringResource(Res.string.listing_not_found_message),
                modifier = Modifier.padding(padding),
            )

            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ListingImage(listing, Modifier.fillMaxWidth().height(240.dp).clip(MaterialTheme.shapes.large))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        formatPrice(listing.price, currentLanguageTag()),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f),
                    )
                    StatusTag(listing.status)
                }
                Text(listing.title, style = MaterialTheme.typography.headlineSmall)
                Text(
                    stringResource(
                        Res.string.detail_meta,
                        stringResource(listing.category.labelRes()),
                        formatDate(listing.creationDate.date, currentLanguageTag()),
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                when (val status = listing.status) {
                    Available -> {}
                    is Reserved -> Text(
                        stringResource(Res.string.status_reserved_by, status.buyer),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                    is Sold -> Text(stringResource(Res.string.status_sold_on, formatDate(status.saleDate, currentLanguageTag())))
                }

                HorizontalDivider()
                Text(
                    listing.description.ifBlank { stringResource(Res.string.no_description) },
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    stringResource(Res.string.sold_by, seller?.name ?: "?"),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                if (isOwner) {
                    HorizontalDivider()
                    when (listing.status) {
                        Available -> Button(onClick = { showReserveDialog = true }, modifier = Modifier.fillMaxWidth()) {
                            Text(stringResource(Res.string.reserve))
                        }
                        is Reserved -> Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = onMarkSold, modifier = Modifier.weight(1f)) {
                                Text(stringResource(Res.string.mark_as_sold))
                            }
                            OutlinedButton(onClick = onMakeAvailable, modifier = Modifier.weight(1f)) {
                                Text(stringResource(Res.string.cancel_reservation))
                            }
                        }
                        is Sold -> OutlinedButton(onClick = onMakeAvailable, modifier = Modifier.fillMaxWidth()) {
                            Text(stringResource(Res.string.put_back_for_sale))
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        if (listing.status !is Sold) {
                            OutlinedButton(onClick = onEdit, modifier = Modifier.weight(1f)) {
                                Text(stringResource(Res.string.edit))
                            }
                        }
                        TextButton(
                            onClick = { showDeleteDialog = true },
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(stringResource(Res.string.delete))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (showReserveDialog) {
        var buyer by rememberSaveable { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showReserveDialog = false },
            title = { Text(stringResource(Res.string.reserve_title)) },
            text = {
                OutlinedTextField(
                    value = buyer,
                    onValueChange = { buyer = it },
                    label = { Text(stringResource(Res.string.field_buyer)) },
                    singleLine = true,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showReserveDialog = false
                        onReserve(buyer)
                    },
                    enabled = buyer.isNotBlank(),
                ) { Text(stringResource(Res.string.reserve)) }
            },
            dismissButton = {
                TextButton(onClick = { showReserveDialog = false }) { Text(stringResource(Res.string.cancel)) }
            },
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(Res.string.delete_title)) },
            text = { Text(stringResource(Res.string.delete_message)) },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDelete()
                }) { Text(stringResource(Res.string.delete), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text(stringResource(Res.string.cancel)) }
            },
        )
    }
}
