package com.example.swapcampus_mamaabbas.composeapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swapcampus_mamaabbas.composeapp.ui.theme.statusColors
import com.example.swapcampus_mamaabbas.shared.domain.ListingStatus
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatusTag(status: ListingStatus) {
    val colors = statusColors(status)
    Surface(color = colors.container, shape = CircleShape) {
        Text(
            text = stringResource(status.labelRes()),
            color = colors.content,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
        )
    }
}
