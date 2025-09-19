package com.transigo.app.admin

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.core.navigation.NavigationRoutes
import com.transigo.app.core.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    dashboardViewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val user by authViewModel.user.collectAsState()
    val dashboardStats by dashboardViewModel.dashboardStats.collectAsState()
    val isLoading by dashboardViewModel.isLoading.collectAsState()
    val errorMessage by dashboardViewModel.errorMessage.collectAsState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) { 
        isVisible = true
    }

    // Enhanced Background - Clean white
    val primaryGradient = Brush.verticalGradient(
        colors = listOf(
            Color.White,
            Color.White
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Modern Header Section
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                ) + fadeIn(animationSpec = tween(1000))
            ) {
                ModernAdminHeader(
                    title = "Admin Command Center",
                    subtitle = "Welcome back, ${user?.name ?: "Administrator"}",
                    onBack = {
                        navController.navigate(NavigationRoutes.HOME) {
                            popUpTo(NavigationRoutes.ADMIN_DASHBOARD) { inclusive = true }
                        }
                    },
                    onRefresh = { dashboardViewModel.refreshStats() },
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(NavigationRoutes.AUTH) { popUpTo(0) { inclusive = true } }
                    },
                    isLoading = isLoading
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Enhanced KPI Dashboard
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                ) + fadeIn(animationSpec = tween(1200))
            ) {
                if (isLoading) {
                    ModernLoadingCard()
                } else {
                    SuperbKpiDashboard(
                        stats = dashboardStats,
                        screenWidth = screenWidth
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Enhanced Quick Actions
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ) + fadeIn(animationSpec = tween(1400))
            ) {
                ModernQuickActionsSection(
                    navController = navController,
                    screenWidth = screenWidth
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
        
        // Floating Action Button
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(1800)) + slideInVertically(
                initialOffsetY = { it },
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            ),
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                onClick = { dashboardViewModel.refreshStats() },
                modifier = Modifier
                    .padding(24.dp)
                    .size(64.dp),
                containerColor = Primary,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 12.dp)
            ) {
                Icon(
                    Icons.Rounded.Refresh,
                    contentDescription = "Refresh",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

data class KpiItem(
    val title: String,
    val value: Int,
    val icon: ImageVector,
    val color: Color,
    val trend: Float = 0f,
    val trendLabel: String = ""
)

@Composable
private fun ModernAdminHeader(
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onLogout: () -> Unit,
    isLoading: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        )
    ) {
        Box {
            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Primary.copy(alpha = 0.08f),
                                Secondary.copy(alpha = 0.05f),
                                Color.Transparent
                            )
                        )
                    )
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Modern Back Button - Clean design
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = Primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Gray900
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Gray600,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Refresh Button - Clean design
                    IconButton(
                        onClick = onRefresh,
                        modifier = Modifier.size(48.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = Secondary
                            )
                        } else {
                            Icon(
                                Icons.Rounded.Refresh,
                                contentDescription = "Refresh",
                                tint = Secondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    
                    // Logout Button - Clean design
                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Rounded.Logout,
                            contentDescription = "Logout",
                            tint = Error,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernLoadingCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    strokeWidth = 4.dp,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Loading Dashboard Analytics...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray600,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun SuperbKpiDashboard(
    stats: com.transigo.app.data.model.DashboardStats,
    screenWidth: Int
) {
    val kpiItems = getEnhancedKpiItems(stats)
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(if (screenWidth > 600) 4 else 2),
        contentPadding = PaddingValues(6.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.height(if (screenWidth > 600) 280.dp else 560.dp)
    ) {
        items(kpiItems) { kpi ->
            SuperbKpiCard(kpi = kpi)
        }
    }
}

@Composable
private fun SuperbKpiCard(
    kpi: KpiItem,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Card(
        modifier = modifier
            .height(160.dp)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { isPressed = !isPressed },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box {
            // Clean gradient background without bubbles
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                kpi.color.copy(alpha = 0.08f),
                                Color.White
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon with clean styling - no background shape
                Box(
                    modifier = Modifier.size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        kpi.icon,
                        contentDescription = null,
                        tint = kpi.color,
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Value with enhanced typography
                Text(
                    text = kpi.value.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Gray900
                )
                
                // Title
                Text(
                    text = kpi.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray600,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun ModernQuickActionsSection(
    navController: NavController,
    screenWidth: Int
) {
    Column {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Gray900
            )
            
            TextButton(
                onClick = { /* Navigate to all tools */ }
            ) {
                Text(
                    "View All",
                    color = Primary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Rounded.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Quick Action Cards
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(getQuickActionItems()) { action ->
                ModernQuickActionCard(
                    action = action,
                    onClick = {
                        when (action.route) {
                            NavigationRoutes.ADMIN_BOOKINGS -> navController.navigate(NavigationRoutes.ADMIN_BOOKINGS)
                            NavigationRoutes.ADMIN_DRIVERS -> navController.navigate(NavigationRoutes.ADMIN_DRIVERS)
                        }
                    }
                )
            }
        }
    }
}

data class QuickAction(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)

@Composable
private fun ModernQuickActionCard(
    action: QuickAction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    
    Card(
        modifier = modifier
            .width(280.dp)
            .height(140.dp)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = action.color)
            ) {
                isPressed = true
                onClick()
            },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            // Clean gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                action.color.copy(alpha = 0.08f),
                                Color.White
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon Container - Clean design without background shape
                Box(
                    modifier = Modifier.size(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        action.icon,
                        contentDescription = null,
                        tint = action.color,
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(20.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = action.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = action.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray600,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                // Arrow icon - Clean design
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.ArrowForward,
                        contentDescription = null,
                        tint = action.color,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

private fun getEnhancedKpiItems(stats: com.transigo.app.data.model.DashboardStats): List<KpiItem> {
    return listOf(
        KpiItem(
            title = "Total Bookings",
            value = stats.totalBookings,
            icon = Icons.Rounded.BookOnline,
            color = Primary,
            trend = 12.5f,
            trendLabel = "vs last month"
        ),
        KpiItem(
            title = "Today's Rides",
            value = stats.bookingsToday,
            icon = Icons.Rounded.Today,
            color = Secondary,
            trend = 8.2f,
            trendLabel = "vs yesterday"
        ),
        KpiItem(
            title = "Monthly Completed",
            value = stats.completedThisMonth,
            icon = Icons.Rounded.CalendarMonth,
            color = Success,
            trend = 15.8f,
            trendLabel = "growth"
        ),
        KpiItem(
            title = "Active Drivers",
            value = stats.activeDrivers,
            icon = Icons.Rounded.Person,
            color = AccentOrange,
            trend = 3.1f,
            trendLabel = "online now"
        ),
        KpiItem(
            title = "Pending Requests",
            value = stats.pendingRequestsCount,
            icon = Icons.Rounded.Pending,
            color = Warning,
            trend = -2.4f,
            trendLabel = "awaiting approval"
        ),
        KpiItem(
            title = "Revenue Today",
            value = 2840, // You can add this to DashboardStats
            icon = Icons.Rounded.AttachMoney,
            color = AccentPurple,
            trend = 18.5f,
            trendLabel = "earnings"
        )
    )
}

private fun getQuickActionItems(): List<QuickAction> {
    return listOf(
        QuickAction(
            title = "Manage Bookings",
            subtitle = "Review, approve and assign drivers",
            icon = Icons.Rounded.BookOnline,
            color = Primary,
            route = NavigationRoutes.ADMIN_BOOKINGS
        ),
        QuickAction(
            title = "Driver Management",
            subtitle = "Oversee driver roster and status",
            icon = Icons.Rounded.DirectionsCar,
            color = Secondary,
            route = NavigationRoutes.ADMIN_DRIVERS
        ),
        QuickAction(
            title = "Analytics Hub",
            subtitle = "View detailed performance metrics",
            icon = Icons.Rounded.Analytics,
            color = AccentPurple,
            route = "admin_analytics"
        ),
        QuickAction(
            title = "User Support",
            subtitle = "Handle customer service issues",
            icon = Icons.Rounded.Support,
            color = Success,
            route = "admin_support"
        )
    )
}
