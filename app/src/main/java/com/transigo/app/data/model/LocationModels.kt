package com.transigo.app.data.model

data class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = ""
)

enum class PaymentMethod(val displayName: String) {
    CASH("Cash"),
    MOBILE_BANKING("Mobile Banking"),
    CARD("Card Payment"),
    WALLET("Digital Wallet")
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