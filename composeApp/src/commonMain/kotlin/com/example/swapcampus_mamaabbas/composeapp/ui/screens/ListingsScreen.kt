package com.example.swapcampus_mamaabbas.composeapp.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.composeapp.ui.components.EmptyState
import com.example.swapcampus_mamaabbas.composeapp.ui.components.ListingCard
import com.example.swapcampus_mamaabbas.composeapp.ui.components.PostButton
import com.example.swapcampus_mamaabbas.composeapp.ui.components.icon
import com.example.swapcampus_mamaabbas.composeapp.ui.components.labelRes
import com.example.swapcampus_mamaabbas.composeapp.viewmodel.ListingsUiState
import com.example.swapcampus_mamaabbas.shared.domain.Category
import org.jetbrains.compose.resources.stringResource

@Composable
fun ListingsScreen(
    uiState: ListingsUiState,
    keyword: String,
    category: Category?,
    userName: String?,
    canPost: Boolean,
    onKeywordChange: (String) -> Unit,
    onCategoryChange: (Category?) -> Unit,
    onListingClick: (Int) -> Unit,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = userName?.let { stringResource(Res.string.greeting_user, it.substringBefore(' ')) }
                    ?: stringResource(Res.string.greeting_guest),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(stringResource(Res.string.listings_headline), style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = keyword,
                onValueChange = onKeywordChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(Res.string.search_hint)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    if (keyword.isNotEmpty()) {
                        IconButton(onClick = { onKeywordChange("") }) {
                            Icon(Icons.Filled.Clear, contentDescription = stringResource(Res.string.clear_search))
                        }
                    }
                },
                shape = MaterialTheme.shapes.extraLarge,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterChip(
                    selected = category == null,
                    onClick = { onCategoryChange(null) },
                    label = { Text(stringResource(Res.string.category_all)) },
                )
                Category.entries.forEach { entry ->
                    FilterChip(
                        selected = category == entry,
                        onClick = { onCategoryChange(entry) },
                        label = { Text(stringResource(entry.labelRes())) },
                        leadingIcon = { Icon(entry.icon(), contentDescription = null, modifier = Modifier.size(18.dp)) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            when (uiState) {
                is ListingsUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                is ListingsUiState.Error -> EmptyState(
                    icon = Icons.Outlined.CloudOff,
                    title = stringResource(Res.string.error_title),
                    message = stringResource(uiState.messageRes),
                )

                is ListingsUiState.Success ->
                    if (uiState.listings.isEmpty()) {
                        EmptyState(
                            icon = Icons.Outlined.SearchOff,
                            title = stringResource(Res.string.no_results_title),
                            message = stringResource(Res.string.no_results_message),
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(bottom = 96.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(uiState.listings, key = { it.id }) { listing ->
                                ListingCard(listing, onClick = { onListingClick(listing.id) })
                            }
                        }
                    }
            }
        }

        if (canPost) {
            PostButton(onClick = onPostClick, modifier = Modifier.align(Alignment.BottomEnd))
        }
    }
}
