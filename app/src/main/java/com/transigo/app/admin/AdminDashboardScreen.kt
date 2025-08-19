package com.transigo.app.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.core.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    dashboardViewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val user by authViewModel.user.collectAsState()
    val dashboardStats by dashboardViewModel.dashboardStats.collectAsState()
    val isLoading by dashboardViewModel.isLoading.collectAsState()
    val errorMessage by dashboardViewModel.errorMessage.collectAsState()

    // Show error message if any
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            // Error is handled in the UI below
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar with logout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { 
                    // Navigate to home instead of back for admin dashboard
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.ADMIN_DASHBOARD) { inclusive = true }
                    }
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Admin Dashboard",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            // Refresh and Logout buttons
            Row {
                IconButton(onClick = { 
                    dashboardViewModel.refreshStats()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
                
                IconButton(onClick = { 
                    authViewModel.logout()
                    navController.navigate(NavigationRoutes.AUTH) {
                        popUpTo(0) { inclusive = true }
                    }
                }) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                }
            }
        }

        // Dashboard content
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Dashboard,
                    contentDescription = "Dashboard",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Admin Dashboard",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Welcome, ${user?.name ?: "Admin"}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Error message
        errorMessage?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(
                        onClick = { dashboardViewModel.clearError() }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // KPI Cards
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading dashboard stats...")
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(280.dp)
            ) {
                items(getKpiItems(dashboardStats)) { kpi ->
                    KpiCard(
                        title = kpi.title,
                        value = kpi.value.toString(),
                        icon = kpi.icon,
                        modifier = Modifier.height(130.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Admin action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedButton(
                onClick = { navController.navigate(NavigationRoutes.ADMIN_BOOKINGS) },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.BookOnline,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(end = 4.dp)
                )
                Text("Bookings", fontSize = 14.sp)
            }

            ElevatedButton(
                onClick = { navController.navigate(NavigationRoutes.ADMIN_DRIVERS) },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(end = 4.dp)
                )
                Text("Drivers", fontSize = 14.sp)
            }
        }
    }
}

data class KpiItem(
    val title: String,
    val value: Int,
    val icon: ImageVector
)

@Composable
fun KpiCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp
            )
        }
    }
}

private fun getKpiItems(stats: com.transigo.app.data.repository.DashboardStats): List<KpiItem> {
    return listOf(
        KpiItem(
            title = "Total Bookings",
            value = stats.totalBookings,
            icon = Icons.Default.BookOnline
        ),
        KpiItem(
            title = "Bookings Today",
            value = stats.bookingsToday,
            icon = Icons.Default.Today
        ),
        KpiItem(
            title = "Completed This Month",
            value = stats.completedThisMonth,
            icon = Icons.Default.CalendarMonth
        ),
        KpiItem(
            title = "Active Drivers",
            value = stats.activeDrivers,
            icon = Icons.Default.Person
        ),
        KpiItem(
            title = "Pending Requests",
            value = stats.pendingRequestsCount,
            icon = Icons.Default.Pending
        )
    )
}
