package com.transigo.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.core.navigation.NavigationRoutes
import com.transigo.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // Observe user state for authentication
    val user by authViewModel.user.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top App Bar with logout button
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TransiGo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                // Welcome message with user name
                user?.name?.let { name ->
                    if (name.isNotEmpty()) {
                        Text(
                            text = "Hi, $name",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
                
                // Logout button
                IconButton(
                    onClick = { authViewModel.logout() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Banner Image with accessibility and responsive design
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_banner_transport),
                    contentDescription = "TransiGo welcome banner featuring city skyline and transportation icons",
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                // Overlay text with better contrast
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome to TransiGo",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Your Travel Companion",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        // Navigation buttons with improved accessibility and responsive design
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Book a Ride button - Primary action
            ElevatedButton(
                onClick = { navController.navigate(NavigationRoutes.BOOKING_FORM) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp), // Minimum touch target
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_booking_ticket),
                    contentDescription = "Booking icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    "Book a Ride", 
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            // Booking History button
            OutlinedButton(
                onClick = { navController.navigate(NavigationRoutes.BOOKING_HISTORY) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp), // Minimum touch target
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "History icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    "Booking History", 
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Profile button
            OutlinedButton(
                onClick = { navController.navigate(NavigationRoutes.PROFILE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp), // Minimum touch target
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    "Profile", 
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Admin Dashboard (only show for admin users)
            user?.userType?.let { userType ->
                if (userType == com.transigo.app.data.model.UserType.ADMIN) {
                    ElevatedButton(
                        onClick = { navController.navigate(NavigationRoutes.ADMIN_DASHBOARD) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Admin Dashboard", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
