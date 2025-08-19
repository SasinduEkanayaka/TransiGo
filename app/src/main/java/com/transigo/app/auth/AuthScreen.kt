package com.transigo.app.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/**
 * Main authentication screen that renders the LoginScreen.
 * This acts as a wrapper for the authentication flow.
 */
@Composable
fun AuthScreen(navController: NavController) {
    // Directly render the LoginScreen as the main auth screen
    LoginScreen(navController = navController)
}
