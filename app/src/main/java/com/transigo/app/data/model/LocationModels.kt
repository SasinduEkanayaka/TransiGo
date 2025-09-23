package com.transigo.app.data.model

data class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = ""
)

enum class PaymentMethod(val displayName: String) {
    CASH("Cash on Ride"),
    CARD("Card Payment")
}

data class BookingPricing(
    val distance: Double = 0.0, // in kilometers
    val pricePerKm: Double = 0.0,
    val totalCost: Double = 0.0
) {
    companion object {
        fun calculate(distance: Double): BookingPricing {
            val pricePerKm = if (distance <= 5.0) 0.8 else 0.6
            val totalCost = distance * pricePerKm
            return BookingPricing(distance, pricePerKm, totalCost)
        }
    }
}

data class SavedCard(
    val id: String = "",
    val cardNumber: String = "",
    val cardholderName: String = "",
    val expiryDate: String = "",
    val cardBrand: String = "",
    val isDefault: Boolean = false
) {
    fun getDisplayNumber(): String {
        return "**** **** **** ${cardNumber.takeLast(4)}"
    }
    
    fun getCardType(): String {
        return when {
            cardNumber.startsWith("4") -> "Visa"
            cardNumber.startsWith("5") || cardNumber.startsWith("2") -> "MasterCard"
            cardNumber.startsWith("3") -> "Amex"
            else -> "Card"
        }
    }
}