package com.example.swapcampus_mamaabbas.composeapp

import androidx.compose.runtime.Composable
import com.example.swapcampus_mamaabbas.composeapp.navigation.AppNavHost
import com.example.swapcampus_mamaabbas.composeapp.ui.theme.SwapCampusTheme

@Composable
fun App() {
    SwapCampusTheme {
        AppNavHost()
    }
}
