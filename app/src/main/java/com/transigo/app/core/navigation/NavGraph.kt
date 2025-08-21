package com.transigo.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.transigo.app.auth.AuthScreen
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.auth.LoginScreen
import com.transigo.app.auth.RegisterScreen
import com.transigo.app.auth.ForgotPasswordScreen
import com.transigo.app.booking.BookingFormScreen
import com.transigo.app.booking.BookingHistoryScreen
import com.transigo.app.profile.ProfileScreen
import com.transigo.app.profile.ProfileViewModel
import com.transigo.app.admin.AdminDashboardScreen
import com.transigo.app.admin.AdminBookingsScreen
import com.transigo.app.admin.AdminDriversScreen
import com.transigo.app.HomeScreen
import com.transigo.app.data.model.UserType
import com.transigo.app.onboarding.OnboardingViewModel
import com.transigo.app.onboarding.SplashScreen
import com.transigo.app.onboarding.OnboardingScreen

/**
 * Main navigation graph for the TransiGo app.
 * Handles authentication-based routing - shows auth screens if not authenticated,
 * otherwise shows the main app screens.
 */
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // Navigation starts at splash which decides between onboarding and auth

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.SPLASH
    ) {
        // Splash decides where to go next based on onboarding state
        composable(NavigationRoutes.SPLASH) {
            SplashScreen(navController = navController)
        }

        // Onboarding flow
        composable(NavigationRoutes.ONBOARDING) {
            val vm: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(navController = navController, viewModel = vm)
        }
        // Authentication flow screens
        composable(NavigationRoutes.AUTH) {
            AuthScreen(navController = navController)
        }
        
        composable("login") {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
        
        composable("register") {
            RegisterScreen(navController = navController, viewModel = authViewModel)
        }
        
        composable("forgot_password") {
            ForgotPasswordScreen(navController = navController, viewModel = authViewModel)
        }
        
        // Main app screens (require authentication)
        composable(NavigationRoutes.HOME) {
            HomeScreen(navController = navController)
        }
        
        composable(NavigationRoutes.BOOKING_FORM) {
            BookingFormScreen(navController = navController)
        }
        
        composable(NavigationRoutes.BOOKING_HISTORY) {
            BookingHistoryScreen(navController = navController)
        }
        
        composable(NavigationRoutes.PROFILE) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }
        
        // Admin screens (require authentication + admin role)
        composable(NavigationRoutes.ADMIN_DASHBOARD) {
            AdminDashboardScreen(navController = navController, authViewModel = authViewModel)
        }
        
        composable(NavigationRoutes.ADMIN_BOOKINGS) {
            AdminBookingsScreen(navController = navController)
        }
        
        composable(NavigationRoutes.ADMIN_DRIVERS) {
            AdminDriversScreen(navController = navController)
        }
    }
}
