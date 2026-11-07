package com.example.swapcampus_mamaabbas.composeapp.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.swapcampus_mamaabbas.composeapp.navigation.ListingsRoute
import com.example.swapcampus_mamaabbas.composeapp.navigation.LoginRoute
import com.example.swapcampus_mamaabbas.composeapp.navigation.MyListingsRoute
import com.example.swapcampus_mamaabbas.composeapp.navigation.ProfileRoute
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppNavigationBar(currentRoute: Any?, isLoggedIn: Boolean, onNavigate: (Any) -> Unit) {
    val accountRoute: Any = if (isLoggedIn) ProfileRoute else LoginRoute
    val items = listOf(
        Triple(ListingsRoute, Icons.Filled.Storefront, Res.string.nav_listings),
        Triple(MyListingsRoute, Icons.Filled.Inventory2, Res.string.my_listings),
        Triple(accountRoute, Icons.Filled.Person, if (isLoggedIn) Res.string.nav_profile else Res.string.log_in),
    )

    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shadowElevation = 6.dp,
    ) {
        NavigationBar(containerColor = Color.Transparent, windowInsets = WindowInsets(0)) {
            items.forEach { (route, icon, label) ->
                NavigationBarItem(
                    selected = currentRoute == route,
                    onClick = { onNavigate(route) },
                    icon = { Icon(icon, contentDescription = null) },
                    label = { Text(stringResource(label)) },
                )
            }
        }
    }
}
