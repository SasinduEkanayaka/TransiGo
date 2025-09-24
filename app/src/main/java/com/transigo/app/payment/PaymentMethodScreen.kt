package com.transigo.app.payment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.transigo.app.auth.AuthViewModel
import com.transigo.app.booking.BookingViewModel
import com.transigo.app.data.model.SavedCard
import com.transigo.app.data.model.PaymentMethod

@Composable
fun PaymentMethodScreen(
    navController: NavController,
    selectedMethod: String? = null,
    bookingViewModel: BookingViewModel = viewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // Card form state
    var cardNumber by remember { mutableStateOf("") }
    var cardholderName by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var saveCard by remember { mutableStateOf(false) }
    var selectedSavedCardId by remember { mutableStateOf<String?>(null) }

    // Collect user and booking state
    val user by authViewModel.user.collectAsState()
    val bookingState by bookingViewModel.state.collectAsState()

    // Load saved cards when user becomes available
    LaunchedEffect(user?.id) {
        val userId = user?.id
        if (!userId.isNullOrBlank()) {
            bookingViewModel.loadSavedCards(userId)
        }
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Payment Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Add New Card Section
            item {
                Text(
                    text = "Add New Card",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Card Number Field
            item {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { 
                        if (it.length <= 19) { // 16 digits + 3 spaces
                            cardNumber = formatCardNumber(it)
                        }
                    },
                    label = { Text("Card Number") },
                    placeholder = { Text("1234 5678 9012 3456") },
                    leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            
            // Cardholder Name Field
            item {
                OutlinedTextField(
                    value = cardholderName,
                    onValueChange = { cardholderName = it },
                    label = { Text("Cardholder Name") },
                    placeholder = { Text("John Doe") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            
            // Expiry Date and CVV Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { 
                            if (it.length <= 5) {
                                expiryDate = formatExpiryDate(it)
                            }
                        },
                        label = { Text("Expiry Date") },
                        placeholder = { Text("MM/YY") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { if (it.length <= 4) cvv = it },
                        label = { Text("CVV") },
                        placeholder = { Text("123") },
                        leadingIcon = { Icon(Icons.Default.Security, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }
            
            // Save Card Checkbox
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { saveCard = !saveCard }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = saveCard,
                        onCheckedChange = { saveCard = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save card details for future payments",
                        fontSize = 14.sp
                    )
                }
            }
            
            // Saved Cards Section
            if (bookingState.savedCards.isNotEmpty()) {
                item {
                    Divider(modifier = Modifier.padding(vertical = 16.dp))
                    Text(
                        text = "Saved Cards",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                items(bookingState.savedCards) { card ->
                    SavedCardItem(
                        card = card,
                        isSelected = selectedSavedCardId == card.id,
                        onSelected = { 
                            selectedSavedCardId = if (selectedSavedCardId == card.id) null else card.id
                            // Clear new card form when selecting saved card
                            if (selectedSavedCardId == card.id) {
                                cardNumber = ""
                                cardholderName = ""
                                expiryDate = ""
                                cvv = ""
                            }
                        }
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        
        // Bottom Confirm Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Button(
                onClick = {
                    val userId = user?.id
                    if (selectedSavedCardId != null) {
                        // Set selected card into view model and mark payment method as CARD
                        bookingState.savedCards.firstOrNull { it.id == selectedSavedCardId }?.let { card ->
                            bookingViewModel.selectCard(card)
                        }
                        navController.previousBackStackEntry?.savedStateHandle?.set("selected_payment_method", PaymentMethod.CARD)
                        navController.popBackStack()
                    } else if (isNewCardValid(cardNumber, cardholderName, expiryDate, cvv) && !userId.isNullOrBlank()) {
                        // Save new card if requested then confirm
                        if (saveCard) {
                            bookingViewModel.saveCard(userId, cardNumber, cardholderName, expiryDate, cvv)
                        }
                        bookingViewModel.updatePaymentMethod(PaymentMethod.CARD)
                        navController.previousBackStackEntry?.savedStateHandle?.set("selected_payment_method", PaymentMethod.CARD)
                        navController.popBackStack()
                    }
                },
                enabled = (selectedSavedCardId != null) || isNewCardValid(cardNumber, cardholderName, expiryDate, cvv),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (selectedSavedCardId != null) "Confirm Payment" else "Add Card & Confirm", 
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun SavedCardItem(
    card: com.transigo.app.data.model.SavedCard,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) 
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary) 
        else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (card.getCardType()) {
                    "Visa" -> Icons.Default.CreditCard
                    "MasterCard" -> Icons.Default.CreditCard
                    "Amex" -> Icons.Default.CreditCard
                    else -> Icons.Default.Payment
                },
                contentDescription = card.getCardType(),
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.getDisplayNumber(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    if (card.isDefault) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Text(
                                text = "Default",
                                fontSize = 10.sp,
                                modifier = Modifier.padding(4.dp, 2.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Text(
                    text = "${card.cardholderName} • ${card.expiryDate}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = card.getCardType(),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Helper functions for formatting input
private fun formatCardNumber(input: String): String {
    val digitsOnly = input.replace(" ", "")
    return digitsOnly.chunked(4).joinToString(" ")
}

private fun formatExpiryDate(input: String): String {
    val digitsOnly = input.replace("/", "")
    return if (digitsOnly.length >= 2) {
        "${digitsOnly.substring(0, 2)}/${digitsOnly.substring(2)}"
    } else {
        digitsOnly
    }
}

private fun isNewCardValid(cardNumber: String, cardholderName: String, expiryDate: String, cvv: String): Boolean {
    return cardNumber.replace(" ", "").length >= 13 &&
            cardholderName.isNotBlank() &&
            expiryDate.length == 5 &&
            cvv.length >= 3
}