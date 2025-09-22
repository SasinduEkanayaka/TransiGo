package com.transigo.app.payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.transigo.app.data.model.PaymentMethod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodScreen(
    navController: NavController,
    selectedMethod: String? = null
) {
    var selectedPaymentMethod by remember { 
        mutableStateOf(
            selectedMethod?.let { PaymentMethod.valueOf(it) } ?: PaymentMethod.MOBILE_BANKING
        ) 
    }
    
    // Card payment form states
    var cardNumber by remember { mutableStateOf("") }
    var cardholderName by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var saveCardInfo by remember { mutableStateOf(false) }
    var showCvv by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Payment Method",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* Notification action */ }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }
        }
        
        // Payment method selection
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Mobile Banking Option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedPaymentMethod == PaymentMethod.MOBILE_BANKING,
                            onClick = { selectedPaymentMethod = PaymentMethod.MOBILE_BANKING }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.AccountBalance,
                        contentDescription = "Mobile Banking",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Mobile Banking",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    RadioButton(
                        selected = selectedPaymentMethod == PaymentMethod.MOBILE_BANKING,
                        onClick = { selectedPaymentMethod = PaymentMethod.MOBILE_BANKING }
                    )
                }
                
                Divider()
                
                // Card Payment Option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedPaymentMethod == PaymentMethod.CARD,
                            onClick = { selectedPaymentMethod = PaymentMethod.CARD }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CreditCard,
                        contentDescription = "Card Payment",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Card Payment",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    RadioButton(
                        selected = selectedPaymentMethod == PaymentMethod.CARD,
                        onClick = { selectedPaymentMethod = PaymentMethod.CARD }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Card Payment Form (shown when Card Payment is selected)
        if (selectedPaymentMethod == PaymentMethod.CARD) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Card Number",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    // Card Number Input
                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = { input ->
                            // Format card number with spaces
                            val digits = input.filter { it.isDigit() }
                            if (digits.length <= 16) {
                                cardNumber = digits.chunked(4).joinToString(" ")
                            }
                        },
                        placeholder = { Text("0000 0000 0000 0000") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            IconButton(onClick = { /* Camera scan */ }) {
                                Icon(Icons.Default.PhotoCamera, contentDescription = "Scan card")
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    
                    Text(
                        text = "Cardholder Name",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    // Cardholder Name Input
                    OutlinedTextField(
                        value = cardholderName,
                        onValueChange = { cardholderName = it },
                        placeholder = { Text("ex. Ashley Smith") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    
                    // Expiry Date and CVV Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Expire Date",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = expiryDate,
                                onValueChange = { input ->
                                    // Format MM/YYYY
                                    val digits = input.filter { it.isDigit() }
                                    expiryDate = when {
                                        digits.length <= 2 -> digits
                                        digits.length <= 6 -> "${digits.take(2)}/${digits.drop(2)}"
                                        else -> "${digits.take(2)}/${digits.drop(2).take(4)}"
                                    }
                                },
                                placeholder = { Text("MM/YYYY") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "CVV/CVC",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = cvv,
                                onValueChange = { input ->
                                    if (input.length <= 4 && input.all { it.isDigit() }) {
                                        cvv = input
                                    }
                                },
                                placeholder = { Text("3-4 digits") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                visualTransformation = if (showCvv) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { showCvv = !showCvv }) {
                                        Icon(
                                            if (showCvv) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            contentDescription = if (showCvv) "Hide CVV" else "Show CVV"
                                        )
                                    }
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                    
                    // Save Card Info Checkbox
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = saveCardInfo,
                            onCheckedChange = { saveCardInfo = it }
                        )
                        Text(
                            text = "Save your information",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Continue Button
        Button(
            onClick = {
                // Navigate back with selected payment method
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "selected_payment_method", 
                    selectedPaymentMethod
                )
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = when (selectedPaymentMethod) {
                PaymentMethod.CARD -> {
                    cardNumber.replace(" ", "").length == 16 &&
                    cardholderName.isNotBlank() &&
                    expiryDate.length == 7 &&
                    cvv.length >= 3
                }
                else -> true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C63FF)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}