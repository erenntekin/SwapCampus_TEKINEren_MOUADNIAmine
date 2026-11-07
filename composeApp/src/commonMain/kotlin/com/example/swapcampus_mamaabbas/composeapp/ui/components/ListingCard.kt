package com.example.swapcampus_mamaabbas.composeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.swapcampus_mamaabbas.composeapp.ui.i18n.currentLanguageTag
import com.example.swapcampus_mamaabbas.composeapp.ui.i18n.formatPrice
import com.example.swapcampus_mamaabbas.composeapp.ui.theme.categoryColors
import com.example.swapcampus_mamaabbas.shared.domain.Listing
import org.jetbrains.compose.resources.stringResource

@Composable
fun ListingImage(listing: Listing, modifier: Modifier = Modifier) {
    val colors = categoryColors(listing.category)
    Box(modifier = modifier.background(colors.container), contentAlignment = Alignment.Center) {
        Icon(
            listing.category.icon(),
            contentDescription = null,
            tint = colors.content,
            modifier = Modifier.fillMaxSize(0.35f),
        )
        listing.photoUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
            )
        }
    }
}

@Composable
fun ListingCard(listing: Listing, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
    ) {
        ListingImage(listing, Modifier.fillMaxWidth().aspectRatio(1f))
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                listing.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                formatPrice(listing.price, currentLanguageTag()),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                stringResource(listing.category.labelRes()),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun ListingRow(listing: Listing, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            ListingImage(listing, Modifier.size(64.dp).clip(MaterialTheme.shapes.small))
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(
                    listing.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(formatPrice(listing.price, currentLanguageTag()), color = MaterialTheme.colorScheme.primary)
            }
            StatusTag(listing.status)
        }
    }
}
