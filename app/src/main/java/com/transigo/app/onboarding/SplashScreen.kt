package com.transigo.app.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.core.navigation.NavigationRoutes
import com.transigo.app.data.model.UserType
import com.transigo.app.R

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

    // Animated values for elegant entrance
    val infiniteTransition = rememberInfiniteTransition(label = "splash_animation")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale_animation"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_animation"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation_animation"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image - on3.jpg
        Image(
            painter = painterResource(id = R.drawable.on3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        // Dark overlay for better text visibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo/Icon with animation
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
                    .alpha(alpha),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "TG",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 36.sp
                        ),
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App Name
            Text(
                text = "TransiGo",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 42.sp,
                    letterSpacing = 2.sp
                ),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(alpha)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Your Journey, Our Priority",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    letterSpacing = 1.sp
                ),
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(alpha)
            )

            Spacer(modifier = Modifier.height(80.dp))

            // Beautiful Loading Indicator
            Box(
                modifier = Modifier.size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer rotating ring
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .scale(scale),
                    color = Color.White.copy(alpha = 0.3f),
                    strokeWidth = 3.dp
                )
                
                // Inner rotating ring
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .alpha(alpha),
                    color = Color.White,
                    strokeWidth = 4.dp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Loading text
            Text(
                text = "Loading your experience...",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    letterSpacing = 0.5.sp
                ),
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}
