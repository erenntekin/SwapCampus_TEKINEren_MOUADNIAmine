package com.example.swapcampus_mamaabbas.composeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.composeapp.ui.components.EmptyState
import com.example.swapcampus_mamaabbas.composeapp.ui.components.ListingRow
import com.example.swapcampus_mamaabbas.composeapp.ui.components.PostButton
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MyListingsScreen(
    listings: List<Listing>,
    canPost: Boolean,
    onListingClick: (Int) -> Unit,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(stringResource(Res.string.my_listings), style = MaterialTheme.typography.headlineMedium)
            Text(
                pluralStringResource(Res.plurals.my_listings_count, listings.size, listings.size),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (listings.isEmpty()) {
                EmptyState(
                    icon = Icons.Outlined.Inventory2,
                    title = stringResource(Res.string.no_my_listings_title),
                    message = stringResource(
                        if (canPost) Res.string.no_my_listings_message else Res.string.admin_no_listings_message,
                    ),
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(listings, key = { it.id }) { listing ->
                        ListingRow(listing, onClick = { onListingClick(listing.id) })
                    }
                }
            }
        }

        if (canPost) {
            PostButton(onClick = onPostClick, modifier = Modifier.align(Alignment.BottomEnd))
        }
    }
}
