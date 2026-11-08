package com.example.swapcampus_mamaabbas.composeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.composeapp.ui.components.labelRes
import com.example.swapcampus_mamaabbas.shared.domain.SellerStats
import com.example.swapcampus_mamaabbas.shared.domain.UserAccount
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileScreen(
    user: UserAccount,
    stats: SellerStats?,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(stringResource(Res.string.profile_title), style = MaterialTheme.typography.headlineMedium)

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(user.name, style = MaterialTheme.typography.titleLarge)
                Text(user.email, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    stringResource(user.role.labelRes()),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        stats?.let {
            Text(stringResource(Res.string.seller_stats), style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatTile(it.posted, Res.string.stat_posted, Modifier.weight(1f))
                StatTile(it.available, Res.string.stat_available, Modifier.weight(1f))
                StatTile(it.reserved, Res.string.stat_reserved, Modifier.weight(1f))
                StatTile(it.sold, Res.string.stat_sold, Modifier.weight(1f))
            }
        }

        OutlinedButton(
            onClick = onLogout,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(Res.string.log_out))
        }
    }
}

@Composable
private fun StatTile(value: Int, label: StringResource, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(value.toString(), style = MaterialTheme.typography.headlineSmall)
            Text(stringResource(label), style = MaterialTheme.typography.labelSmall, maxLines = 1)
        }
    }
}
