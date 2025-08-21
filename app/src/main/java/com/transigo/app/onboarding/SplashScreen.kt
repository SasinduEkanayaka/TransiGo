package com.transigo.app.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.core.navigation.NavigationRoutes
import com.transigo.app.data.model.UserType

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val completed by viewModel.isCompleted.collectAsState()
    val user by authViewModel.user.collectAsState()

    LaunchedEffect(completed, user) {
        if (!completed) {
            navController.navigate(NavigationRoutes.ONBOARDING) {
                popUpTo(NavigationRoutes.SPLASH) { inclusive = true }
            }
            return@LaunchedEffect
        }

        val currentUser = user
        when {
            currentUser == null -> {
                navController.navigate(NavigationRoutes.AUTH) {
                    popUpTo(NavigationRoutes.SPLASH) { inclusive = true }
                }
            }
            currentUser.userType == UserType.ADMIN -> {
                navController.navigate(NavigationRoutes.ADMIN_DASHBOARD) {
                    popUpTo(NavigationRoutes.SPLASH) { inclusive = true }
                }
            }
            else -> {
                navController.navigate(NavigationRoutes.HOME) {
                    popUpTo(NavigationRoutes.SPLASH) { inclusive = true }
                }
            }
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}
